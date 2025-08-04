package com.door.batch

import com.door.batch.helper.annotations.IntegrationTest
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test

@IntegrationTest
class H2TableConnectionTests {

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    fun printTableSchemas() {
        // 테이블 목록 조회
        println("✅ 테이블 정보 조회 테스트")

        val tables = entityManager
            .createNativeQuery(
                """
                SELECT TABLE_NAME 
                FROM INFORMATION_SCHEMA.TABLES 
                WHERE TABLE_SCHEMA = 'PUBLIC'
                """
            )
            .resultList

        println("✅ H2 Database Schema 조회 ===")
        // 각 테이블별로 컬럼 정보 조회
        tables.forEach { tableName ->
            val columns = entityManager
                .createNativeQuery(
                    """
                    SELECT COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, IS_NULLABLE
                    FROM INFORMATION_SCHEMA.COLUMNS 
                    WHERE TABLE_SCHEMA = 'PUBLIC' AND TABLE_NAME = '$tableName'
                    ORDER BY ORDINAL_POSITION
                    """
                )
                .resultList

            println("\nTable: $tableName")
            columns.forEach { column ->
                val columnInfo = column as Array<*>
                println(
                    "  Column: ${columnInfo[0]}, Type: ${columnInfo[1]}, " +
                            "Length: ${columnInfo[2]}, Nullable: ${columnInfo[3]}"
                )
            }
        }
        println("=========================")
    }
}
