package com.tharushi.tomatogame.controller.PlayerController;

/**
 * @author Tharushi Welarathna <nirmanitharushi1@gmail.com>
 * @since 12/6/2023
 */
import com.tharushi.tomatogame.dto.game.TableDataDto;
import com.tharushi.tomatogame.service.TableDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/table")
@RequiredArgsConstructor
public class TableDataController {

    private final TableDataService tableDataService;

    @GetMapping("/data")
    public ResponseEntity<List<TableDataDto>> getAllTableData() {
        List<TableDataDto> tableDataList = tableDataService.getAllTableData();
        return ResponseEntity.ok(tableDataList);
    }
}
