// api/src/main/java/com/example/metropolitanapi/api/web/controller/MemberController.java
package com.example.metropolitanapi.api.web.controller;

import com.example.metropolitanapi.members.application.*;
import com.example.metropolitanapi.members.domain.model.Member;
import com.example.metropolitanapi.sharedkernel.model.CalendarEntry;
import com.example.metropolitanapi.sharedkernel.paging.PageResult;
import com.example.metropolitanapi.api.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jacidi/members")
@Tag(name = "Members", description = "CRUD de miembros y gestión de calendario")
public class MemberController {
    private final CreateMemberUseCase createUC;
    private final GetMemberUseCase getUC;
    private final UpdateMemberUseCase updateUC;
    private final DeleteMemberUseCase deleteUC;
    private final ListCalendarUseCase listCalUC;
    private final AssignActivityUseCase assignUC;
    private final CancelActivityUseCase cancelUC;
    private final ListMembersPagedUseCase listMembersUC;

    public MemberController(CreateMemberUseCase createUC,
                            GetMemberUseCase getUC,
                            UpdateMemberUseCase updateUC,
                            DeleteMemberUseCase deleteUC,
                            ListCalendarUseCase listCalUC,
                            AssignActivityUseCase assignUC,
                            CancelActivityUseCase cancelUC,
                            ListMembersPagedUseCase listMembersUC) {
        this.createUC = createUC; this.getUC = getUC; this.updateUC = updateUC; this.deleteUC = deleteUC;
        this.listCalUC = listCalUC; this.assignUC = assignUC; this.cancelUC = cancelUC; this.listMembersUC = listMembersUC;
    }

    @Operation(summary = "Listar miembros (paginado)")
    @Parameters({
            @Parameter(name = "limit", description = "Cantidad de elementos por página (1..100)", schema = @Schema(type = "integer", minimum = "1", maximum = "100"), example = "20"),
            @Parameter(name = "offset", description = "Desplazamiento inicial (>=0)", schema = @Schema(type = "integer", minimum = "0"), example = "0")
    })
    @ApiResponse(responseCode = "200", description = "Página de miembros",
            content = @Content(schema = @Schema(implementation = PageResult.class)))
    @GetMapping
    public PageResult<Member> list(@RequestParam(name = "limit", defaultValue = "20") int limit,
                                   @RequestParam(name = "offset", defaultValue = "0") int offset) {
        int safeLimit = Math.min(Math.max(limit, 1), 100); // 1..100
        int safeOffset = Math.max(offset, 0);
        return listMembersUC.execute(safeLimit, safeOffset);
    }

    @Operation(summary = "Crear miembro", description = "El id se genera automáticamente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creado",
                    content = @Content(schema = @Schema(implementation = Member.class))),
            @ApiResponse(responseCode = "400", description = "Petición inválida")
    })
    @PostMapping
    public ResponseEntity<Member> create(@RequestBody MemberUpsertRequest req){
        Member toCreate = new Member(null, req.name(), req.dni(), req.city(), new ArrayList<>());
        Member saved = createUC.execute(toCreate);
        return ResponseEntity.created(URI.create("/jacidi/members/"+saved.getId())).body(saved);
    }

    @Operation(summary = "Obtener miembro por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Member.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @GetMapping("/{id}")
    public Member get(@PathVariable Long id){ return getUC.execute(id); }

    @Operation(summary = "Actualizar parcialmente un miembro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actualizado", content = @Content(schema = @Schema(implementation = Member.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @PutMapping("/{id}")
    public Member update(@PathVariable Long id, @RequestBody MemberUpdateRequest req){
        Member patch = new Member(null, req.name(), req.dni(), req.city(), null);
        return updateUC.execute(id, patch);
    }

    @Operation(summary = "Eliminar miembro")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminado"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){ deleteUC.execute(id); return ResponseEntity.noContent().build(); }

    @Operation(summary = "Listar calendario de un miembro",
            description = "Permite filtrar opcionalmente por state_id")
    @Parameters({
            @Parameter(name = "state_id", description = "Id del estado (p. ej. 1=reserva, 2=cancelar, 3=realizado, 4=ausente)",
                    schema = @Schema(type = "integer", format = "int64"), required = false)
    })
    @ApiResponse(responseCode = "200", description = "Listado",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CalendarEntry.class))))
    @GetMapping("/{memberId}/calendar")
    public List<CalendarEntry> listCalendar(@PathVariable Long memberId,
                                            @RequestParam(name = "state_id", required = false) Long stateId){
        return listCalUC.execute(memberId, stateId);
    }

    @Operation(summary = "Asignar actividad al calendario")
    @ApiResponse(responseCode = "200", description = "Miembro actualizado",
            content = @Content(schema = @Schema(implementation = Member.class)))
    @PostMapping("/{memberId}/calendar")
    public Member assign(@PathVariable Long memberId,
                         @RequestBody @Valid AssignActivityRequest body){
        return assignUC.execute(memberId, body.activityId(), body.stateId());
    }


    @Operation(summary = "Cancelar/cambiar estado de una actividad del calendario",
            description = "Si no se envía state_id, se usa el estado 'cancelar' por defecto")
    @Parameters({
            @Parameter(name = "state_id", description = "Id del estado de cancelación (opcional)",
                    schema = @Schema(type = "integer", format = "int64"), required = false)
    })
    @ApiResponse(responseCode = "200", description = "Miembro actualizado",
            content = @Content(schema = @Schema(implementation = Member.class)))
    @DeleteMapping("/{memberId}/calendar/{activityId}")
    public Member cancel(@PathVariable Long memberId,
                         @PathVariable Long activityId,
                         @RequestParam(name = "state_id", required = false) Long cancelStateId){
        return cancelUC.execute(memberId, activityId, cancelStateId);
    }
}
