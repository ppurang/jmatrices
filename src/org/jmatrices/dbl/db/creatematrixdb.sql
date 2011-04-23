--connect to matrixdb
drop table matrixTable;

CREATE TABLE matrixTable (
	uuid	CHAR(36) NOT NULL,
	row	    SMALLINT NOT NULL,
	col	    SMALLINT NOT NULL,
	value	DOUBLE PRECISION NOT NULL,

	CONSTRAINT ckMatrixTableRow CHECK(row > 0),
	CONSTRAINT ckMatrixTableCol CHECK(col > 0),
	CONSTRAINT uqMatrixTableUUIDRowCol UNIQUE(uuid,row,col)
);