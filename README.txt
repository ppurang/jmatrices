JMATRICES Matrix Library

To use the library, please read the Draft document to get the bigger picture of how the library is organized.

To use the library please use the following steps.

1. Matrix objects can be created using static methods in the class org.jmatrices.matrix.MatrixFactory. Please check
   the javadocs for further details.
2. Once you have an empty or prefilled matrix, you are ready to perform operations on it.
3. If you have an empty matrix you can set element values using the only "set" method in the matrix interface.
4. To perform an operation you have to first decide which of the following patterns your intended operation fits.
5. Operation patterns
    a. [measure] A -> s ,where A is a matrix and s is a scalar(number) or boolean.
        examples include, rank, determinant, isSquare, isIdempotent, sum, mean, isDiagonal etc.
    b. [transform] A -> B, where a matrix A is transformed into another matrix B
        examples include transpose, inverse, element-by-element operations
    c. [row column transform] A -> V, where A is transformed into a Row or Column vector V
        examples include sum, product, mean of rows or columns
    d. [operate] A,B -> C where given two matrices A and B, we operate on them to get a matrix C
        examples include solving, matrix addition, multiplication, subtraction, element-by-element multiplication etc.
    e. [decompose] A -> C,D,..,s,r,t... where C,D,.. are matrices and s,r,t are scalars
        examples include LU, SVD, QR, Eigenvalue and Cholesky decompositions
6. Once you have decided you can check out the packages
        operation type -> package
    a. [measure] -> measure
    b. [transform] -> transformer
    c. [row column transform] -> rowcoltr
    d. [operate] -> operator
    e. [decompose] -> decomposition
7. Now you are on your own .. Have fun exploring!

Remember that your comments are sought after.

