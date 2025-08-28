package kr.hhplus.be.server.domain.point.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.domain.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

    private final PointService pointService;

    @Operation(
        summary = "포인트 충전",
        requestBody = @RequestBody(
            description = "충전 금액",
            required = true,
            content = @Content(schema = @Schema(type = "integer", example = "1000"))
        )
    )
    @PatchMapping("{id}/charge")
    public User charge(@Valid @Parameter(description = "충전할 회원 id") @PathVariable long userId, @RequestBody long amount) {
        return pointService.chargePoint(userId, amount);
    }

    @Operation(
        summary = "포인트 사용",
        requestBody = @RequestBody(
            description = "사용 금액",
            required = true,
            content = @Content(schema = @Schema(type = "integer", example = "500"))
        )
    )
    @PatchMapping("{id}/use")
    public User use(@Valid @Parameter(description = "사용할 회원 id") @PathVariable long userId, @RequestBody long amount) {
        return pointService.usePoint(userId, amount);
    }
}

