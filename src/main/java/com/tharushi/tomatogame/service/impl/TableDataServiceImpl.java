

package com.tharushi.tomatogame.service.impl;

/**
 * @author Tharushi Welarathna <nirmanitharushi1@gmail.com>
 * @since 12/6/2023
 */
import com.tharushi.tomatogame.dto.game.TableDataDto;
import com.tharushi.tomatogame.repository.TableDataRepository;
import com.tharushi.tomatogame.service.TableDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TableDataServiceImpl implements TableDataService {

    private final TableDataRepository tableDataRepository;

    @Override
    public List<TableDataDto> getAllTableData() {
        // Implement logic to fetch data from the repository and convert it to TableDataDto
        // For example:
        // List<YourEntity> dataFromRepository = tableDataRepository.findAll();
        // List<TableDataDto> tableDataList = convertToTableDataDto(dataFromRepository);
        // return tableDataList;

        // For simplicity, assuming you have a direct method to fetch DTOs from the repository:
        return tableDataRepository.getAllTableData(); // Adjust this based on your repository method.
    }


}
