import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            int num = input.nextInt();
            a[i] = num;
        }
        for(int i=1;i<n;i++){
            for(int j=0;j<i;j++){
                if(a[i]<a[j]){
                    int tmp = a[i];
                    a[i] = a[j];
                    a[j] = tmp;
                }
            }
        }
        for (int num:a){
            System.out.print(num+" ");
        }
        input.close();
    }
}