JMATRICES Matrix Library (0.6)

---------------version 0.5---------------
	To use the library, please read the Draft document (released with version 0.5) to get the bigger picture of how the library is organized.
	
	To use the library please use the following steps.
	
	1. Matrix objects can be created using static methods in the class org.jmatrices.dbl.MatrixFactory. Please check
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

---------------version 0.6---------------
FILES
	jmatrices_all_0_6.zip				contains all the other files.
	jmatrices0.6.jar						class files
	jmatrices_src_0_6.zip				source files
	jmatrices_javadoc_0_6.zip		javadoc files
	jmatrices_shell_0_6.jar	      shell distribution (see below SHELL)

RELEASE NOTES
	Now there are two ways of using this library. 
	1. LIBRARY 
		As a library to be used by other libraries or programs. In this form it will be used in steps mentioned above. Just check the CHANGES below to see if there have been any changes.
	
	2. SHELL
		Shell is a JavaScript console that allows interactive usage of the library in the spirit of mathematical products like Matlab and Gauss. Right now syntax supporting Matlab is available. 
		Start the shell java -jar jmatrices_shell_0_6.jar. Once the console is ready, type the following 
		 js> ms = ms = Packages.org.jmatrices.dbl.client.MatlabSyntax
		 js> A = ms.create("[1,2,3;4,5,6;7,8,9]");
		 js> B=ms.rand(3,3);
		 js> C=ms.multiply(A,B);
		 js> invB = ms.inv(B);
		 
		 for help type 
		 js> ms.help();
		 
		 You can even write scripts and run them through menu "File">"Load". 
		 
		 Enjoy!
		


CHANGES
	[0.55][package reorganization] org.jmatrices is the main package. Matrices of double are found under org.jmatrices.dbl (compare with org.jmatrices.bigdecimal and org.jmatrices.complex)
	[0.55][created new project jcomplex] http://sourceforge.net/projects/jcomplex
	[0.6][MatrixFactory]implementation propogation / Refactor MatrixFactory
	[0.6][MatrixFactory]Matrixfactory.getMatrix from a List!
	[0.6][MatrixTransformer] implement tril, triu, get(extract) and put(embed) diag as in matlab
	[0.6][MatrixTransformer]cummulativeSum (cumulativeColumnSum), cummulativeProduct(cumulativeColumnProduct)
	[0.6][MatrixOperator]kronecker product and horizontal direct product.
	[0.6][MatrixProperty] isLowerTriangular and isUpperTriangular
	[0.6][LightMatrixImpl]Fixed a bug in LightMatrixImpl getRow(int)
	[0.6][package client] client is a package that groups together Syntax implementations
	[0.6][MatlabSyntax]Matlab Syntax (in package org.jmatrices.dbl.client) for people familiar with Matlab.
	[0.6][MatlabSyntax]blkdiag (block diagonal)


TODO 
	Junit tests
	[GaussSyntax]Gauss Syntax
	matrix string output configurable (Localization and format)
	bigdecimal
	complex (jcomplex)




