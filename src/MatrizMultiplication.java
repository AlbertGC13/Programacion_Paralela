import mpi.*;

public class MatrizMultiplication {
    public static void main(String[] args) throws MPIException {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int N = 4; // Tamaño de las matrices
        int[][] A = new int[N][N];
        int[][] B = new int[N][N];
        int[][] C = new int[N][N];

        if (rank == 0) {
            // Inicializar matrices A y B
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    A[i][j] = i + j;
                    B[i][j] = i - j;
                }
            }
        }

        // Distribuir matrices A y B a todos los procesos
        int[] bufferA = new int[N * N];
        int[] bufferB = new int[N * N];
        if (rank == 0) {
            for (int i = 0; i < N; i++) {
                System.arraycopy(A[i], 0, bufferA, i * N, N);
                System.arraycopy(B[i], 0, bufferB, i * N, N);
            }
        }
        MPI.COMM_WORLD.Bcast(bufferA, 0, N * N, MPI.INT, 0);
        MPI.COMM_WORLD.Bcast(bufferB, 0, N * N, MPI.INT, 0);

        // Convertir buffer a matrices A y B locales
        for (int i = 0; i < N; i++) {
            System.arraycopy(bufferA, i * N, A[i], 0, N);
            System.arraycopy(bufferB, i * N, B[i], 0, N);
        }

        // Calcular porciones de la matriz C
        int rowsPerProcess = N / size;
        int startRow = rank * rowsPerProcess;
        int endRow = (rank + 1) * rowsPerProcess;

        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < N; j++) {
                C[i][j] = 0;
                for (int k = 0; k < N; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        // Recopilar resultados parciales en el proceso raíz
        int[] bufferC = new int[N * N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(C[i], 0, bufferC, i * N, N);
        }

        int[] gatheredBufferC = new int[N * N];
        MPI.COMM_WORLD.Gather(bufferC, startRow * N, rowsPerProcess * N, MPI.INT, gatheredBufferC, startRow * N, rowsPerProcess * N, MPI.INT, 0);

        if (rank == 0) {
            // Convertir buffer recopilado a matriz C
            for (int i = 0; i < N; i++) {
                System.arraycopy(gatheredBufferC, i * N, C[i], 0, N);
            }

            // Imprimir matriz resultante C
            System.out.println("Resultado de la multiplicación de matrices:");
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    System.out.print(C[i][j] + " ");
                }
                System.out.println();
            }
        }

        MPI.Finalize();
    }
}
