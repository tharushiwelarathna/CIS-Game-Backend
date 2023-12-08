package com.tharushi.tomatogame.service;

/**
 * @author Tharushi Welarathna <nirmanitharushi1@gmail.com>
 * @since 12/6/2023
 */
import com.tharushi.tomatogame.dto.game.TableDataDto;

import java.util.List;

public interface TableDataService {
    List<TableDataDto> getAllTableData();
}