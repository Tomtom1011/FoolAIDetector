package domain.augmentation.types.image.filter;

public class FilterAugmentationHomberger {

    public static void main(String args[]) {
        int n = 10;
        int m = 10;
        int[][] picture = init(n,m);
        int[][] filter  = {{0,0,0},{0,1,0},{0,0,0}};
// int[][] filter  = {{0,0,0,0,0},{0,0,0,0,0},{0,0,1,0,0},{0,0,0,0,0},{0,0,0,0,0}};
        int[][] newPicture = faltung(picture, filter);
        ausgabe(picture);
        System.out.println();
        ausgabe(newPicture);
    }

    public static int[][] faltung(int[][] picture, int[][] filter){
        int n = picture.length;
        int m = picture[0].length;
        int k = filter.length;
        int mitte = 1 + k/2;
        System.out.println(mitte);
        int[][] result = new int[n][m];

        for(int i=0;i<n;i++) {
            for(int j=0;j<m;j++) {
//Faltung berechnen
//Doppel-Schleife ueber filter
                int skalarprodukt = 0;

                for(int fi=0;fi<filter.length;fi++) {
                    for(int fj=0;fj<filter[0].length;fj++) {
                        int ii = i - mitte + 1 + fi;
                        int jj = j - mitte + 1 + fj;

                        if(ii>=0 && ii<n && jj>=0 && jj<m) {
                            skalarprodukt += picture[ii][jj]*filter[fi][fj];
                            System.out.println(i + " " + j + "|" + fi + " " + fj + "|" + ii + " " + jj);
                        }
                    }
                }

                result[i][j] = skalarprodukt;
            }
        }
        return result;
    }

    public static int[][] init(int n, int m){
        int max = 10;
        int[][] result = new int[n][m];
        for(int i=0;i<n;i++) {
            for(int j=0;j<m;j++) {
                result[i][j] = (int)(Math.random()*max);
            }
        }
        return result;
    }

    public static void ausgabe(int[][] matrix){
        for(int i=0;i<matrix.length;i++) {
            for(int j=0;j<matrix.length;j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

}