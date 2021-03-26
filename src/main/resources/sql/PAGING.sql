WITH QUERY_RESULT AS (
    ${QUERY_STRING}
)

SELECT
    *
FROM
    (
        SELECT
            ROW_NUMBER() OVER(ORDER BY ${ORDER_BY}) RowNumber,
            *,
            (SELECT COUNT(*) FROM (SELECT * FROM QUERY_RESULT) T ) AS recordsTotal,
            (SELECT COUNT(*) FROM (SELECT * FROM QUERY_RESULT) T ) AS recordsFiltered
        FROM
            (SELECT * FROM QUERY_RESULT) T
    ) T
WHERE
    RowNumber >= ${START}
AND RowNumber <= ${ENDED}