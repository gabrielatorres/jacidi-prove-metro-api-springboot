package com.example.metropolitanapi.api.web.controller;

import com.example.metropolitanapi.members.application.ListMemberActivitiesUseCase;
import com.example.metropolitanapi.sharedkernel.model.ActivitySnapshot;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jacidi")
@Tag(name = "Activities", description = "Consultas de actividades")
public class ActivityController {
    private final ListMemberActivitiesUseCase listMemberActivities;

    public ActivityController(ListMemberActivitiesUseCase listMemberActivities) {
        this.listMemberActivities = listMemberActivities;
    }

    @Operation(summary = "Listar actividades del calendario de un miembro",
            description = "Devuelve las actividades presentes en el calendario del miembro. " +
                    "Se puede filtrar opcionalmente por spaces_id.")
    @GetMapping("/activity/{memberId}")
    public List<ActivitySnapshot> listForMember(
            @PathVariable Long memberId,
            @Parameter(description = "Id del espacio para filtrar (opcional)")
            @RequestParam(name = "spaces_id", required = false) Long spacesId) {
        return listMemberActivities.execute(memberId, spacesId);
    }
}
