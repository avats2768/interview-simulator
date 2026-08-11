package com.simulator.backend.skills;

import com.simulator.backend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillsController {

    private final SkillsService skillsService;

    /**
     * Get all skills.
     *
     * GET /skills
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<SkillsEntity>>> getAllSkills() {

        List<SkillsEntity> skills =
                skillsService.getAllSkills();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Skills fetched successfully.",
                        skills
                )
        );
    }

    /**
     * Get skills by UUIDs.
     *
     * POST /skills/by-ids
     *
     * Request:
     *
     * [
     *     "uuid-1",
     *     "uuid-2"
     * ]
     */
    @PostMapping("/by-ids")
    public ResponseEntity<ApiResponse<List<SkillsEntity>>> getSkillsByIds(
            @RequestBody List<String> skillUuids
    ) {

        List<SkillsEntity> skills =
                skillsService.getSkillsByIds(
                        skillUuids
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Skills fetched successfully.",
                        skills
                )
        );
    }
}