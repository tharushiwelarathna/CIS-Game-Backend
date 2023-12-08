package com.tharushi.tomatogame.repository;

/**
 * @author Tharushi Welarathna <nirmanitharushi1@gmail.com>
 * @since 12/6/2023
 */
import com.tharushi.tomatogame.dto.game.TableDataDto;
import com.tharushi.tomatogame.entity.TableDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableDataRepository extends JpaRepository<TableDataEntity, Long> {

    // Custom query to fetch data as DTOs directly
    @Query("SELECT new com.tharushi.tomatogame.dto.game.TableDataDto(td.date, td.player, td.level, td.points) FROM TableDataEntity td")
    List<TableDataDto> getAllTableData();
}