package ru.romzheln.data_generator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseCleanupService {

    @Qualifier("listingJdbcTemplate")
    private final JdbcTemplate listingJdbcTemplate;

    @Qualifier("searchJdbcTemplate")
    private final JdbcTemplate searchJdbcTemplate;


    public void clearListingDatabase() {
        listingJdbcTemplate.execute("""
        DO $$
        DECLARE
            tables_to_truncate TEXT;
        BEGIN
            SELECT string_agg(
                format('%I.%I', n.nspname, c.relname),
                ', '
            )
            INTO tables_to_truncate
            FROM pg_class c
            JOIN pg_namespace n ON n.oid = c.relnamespace
            WHERE n.nspname = 'public'
              AND c.relkind IN ('r', 'p')
              AND c.relname NOT IN (
                  'databasechangelog',
                  'databasechangeloglock'
              )
              AND NOT EXISTS (
                  SELECT 1
                  FROM pg_inherits i
                  WHERE i.inhrelid = c.oid
              );

            IF tables_to_truncate IS NOT NULL THEN
                EXECUTE 'TRUNCATE TABLE '
                    || tables_to_truncate
                    || ' RESTART IDENTITY CASCADE';
            END IF;
        END $$;
        """);
    }


    public void clearSearchDatabase() {
        searchJdbcTemplate.execute("""
        DO $$
        DECLARE
            tables_to_truncate TEXT;
        BEGIN
            SELECT string_agg(
                format('%I.%I', n.nspname, c.relname),
                ', '
            )
            INTO tables_to_truncate
            FROM pg_class c
            JOIN pg_namespace n ON n.oid = c.relnamespace
            WHERE n.nspname = 'public'
              AND c.relkind IN ('r', 'p')
              AND c.relname NOT IN (
                  'databasechangelog',
                  'databasechangeloglock'
              )
              AND NOT EXISTS (
                  SELECT 1
                  FROM pg_inherits i
                  WHERE i.inhrelid = c.oid
              );

            IF tables_to_truncate IS NOT NULL THEN
                EXECUTE 'TRUNCATE TABLE '
                    || tables_to_truncate
                    || ' RESTART IDENTITY CASCADE';
            END IF;
        END $$;
        """);
    }

}
