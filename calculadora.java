package javaapplication16;

import java.util.Scanner;

public class JavaApplication16 {

    public static int nBit = 10;  // operacoes de binarios com n - 1  bits, 1 é o bit de sinal
    public static int carry = 0; // auxilio para soma "vai um"
        
    public static double Decimal(int[] a) { // transforma um array de binario com tamanho tam, sem bit de sinal em int
        
        double resultado = 0;
        int tam = divAux1(a);
        for (int x = tam - 1; x > 0; x--) {
            if (a[x] == 1) 
                resultado = Math.pow(2,a.length - 1 - x) + resultado;
        }
              
      return resultado;
    }

    public static int[] Binario(int a) { // retorna int a em binario (array) 

        int[] bin = new int[nBit];

        if (a > 0) {
            bin[0] = 0;
        }
        if (a < 0) {
            bin[0] = 1;
            a = -a;
        }
        for (int i = nBit - 1; i > 0; i--) {
            if (a % 2 == 0) {
                bin[i] = 0;
            }
            if (a % 2 == 1) {
                bin[i] = 1;
            }
            a = a / 2;
        }

        return bin;
    }

    public static int[] SomaBin(int[] a, int[] b) { // soma recebendo arranjo de binarios

        int[] resultado = new int[nBit + 1];
        int i = nBit - 1;
        while (i >= 0) {

            if (carry == 0) {

                if (a[i] == 0 && b[i] == 0) {
                    resultado[i] = 0;
                }
                if ((a[i] == 1 && b[i] == 0) || (a[i] == 0 && b[i] == 1)) {
                    resultado[i] = 1;
                }
                if (a[i] == 1 && b[i] == 1) {
                    resultado[i] = 0;
                    carry = 1;
                }
                i--;
            }

            if (carry == 1) {

                if (a[i] == 0 && b[i] == 0) {
                    resultado[i] = 1;
                    carry = 0;
                }
                if ((a[i] == 1 && b[i] == 0) || (a[i] == 0 && b[i] == 1)) {
                    resultado[i] = 0;
                    carry = 1;
                }
                if (a[i] == 1 && b[i] == 1) {
                    resultado[i] = 1;
                    carry = 1;
                }
                i--;

            }
        }

        return (resultado);

    }

    public static int[] SomaInt(int n1, int n2) { // soma recebendo dois inteiros
        int[] a = Binario(n1);
        int[] b = Binario(n2);
        int[] resultado = SomaBin(a, b);
        
        if (n1 == 0) {
            for (int y = 0; y < nBit; y++) {
                System.out.print(b[y]);
            }
            return b;
        }
        if (n2 == 0) {
            for (int y = 0; y < nBit; y++) {
                System.out.print(a[y]);
            }
            return a;

        }

        if (n1 < 0 && n2 > 0) {
            Sub(n2, -n1);
        }
        if (n1 > 0 && n2 < 0) {
            Sub(n1, -n2);
        }
        if (n1 < 0 && n2 < 0) {
            Sub(-n2, n1);
        }

        if (n1 > 0 && n2 > 0) {
            for (int y = 0; y < nBit; y++) {
                System.out.print(resultado[y]);

            }
        }

        return (SomaBin(a, b));
    }

    public static int[] Complemento2(int[] a) {

        for (int x = 0; x < nBit; x++) { // troca os 0's por 1's do binario
            if (a[x] == 1) {
                a[x] = 0;
            } else {
                a[x] = 1;
            }
        }
        int[] comp = SomaBin(a, Binario(1)); // soma 1 

        return comp;
    }

    public static int[] Sub(int n1, int n2) { // sub com bit de sinal usa duas vezes o comp de 2
        int[] a = Binario(n1);
        int[] b = Binario(n2);
        b[0] = 1;
        int[] resultado = new int[nBit];

        if (n1 == 0) {
            for (int y = 0; y < nBit; y++) {
                System.out.print(b[y]);
            }
            return b;
        }
        if (n2 == 0) {
            for (int y = 0; y < nBit; y++) {
                System.out.print(a[y]);
            }
            return a;

        }
        if (n1 > 0 && n2 < 0) {
            resultado = SomaInt(n1, -n2);
        }

        if (n1 > 0 && n2 > 0) {
            if (n1 > n2) {
                int[] comp = Complemento2(b);
                resultado = SomaBin(a, comp);
                resultado[0] = 0;
                for (int y = 0; y < nBit; y++) {
                    System.out.print(resultado[y]);
                }
            }

            if (n2 > n1) {
                int[] comp = Complemento2(a);
                resultado = SomaBin(b, comp);
                resultado[0] = 1;

                for (int y = 0; y < nBit; y++) {
                    System.out.print(resultado[y]);
                }
            }
        }
        if (n1 < 0 && n2 > 0) {

            resultado = SomaBin(Binario(-n1), Binario(n2));
            resultado[0] = 1;
            for (int y = 0; y < nBit; y++) {
                System.out.print(resultado[y]);
            }
        }

        if (n1 < 0 && n2 < 0) {
            Sub(-n2, -n1);
        }

        return resultado;

    }
    
    public static int[] div(int n1, int n2) { // n1 dividido por n2
        int[] a = Binario(n1);
        int[] b = Binario(n2);
        int[] c = new int[nBit]; 
        for (int y = 0; y < nBit - 1; y++)
            c[y] = 2;
        int d1 = divAux1(c);
        int[] d2 = divAux2(c);
        
        int x = 0;
       
        do { c[x] = a[x];
             x++;          
             d1 = divAux1(c);
             d2 = divAux2(c);
        }
        while (Decimal(d2) <= n2);        
        
        for (int z = 0; z < nBit; z++)
            System.out.print(d2[z]);
                   
        return d2;
        
    }
    
    
    public static int divAux1 (int[] a) {
        
       int tam = 0;
        
        for(int i = 0; i < a.length; i++){
            if (a[i] == 1 || a[i] == 0)
                tam++;        
            }
                    
        return tam;
        
    }
    
     public static int[] divAux2 (int[] a) {
         int tam = divAux1(a);
         int[] array = new int[tam];
                
        for(int j = 0; j < tam; j++){
            array[j] = a[j]; 
        }
                
        return array;
     }
    
    public static void main(String[] args) {
        // Sub(-2, -14); // subtrai inteiros
        // SomaInt(0,-2); // soma inteiros
        //System.out.println(Decimal(Binario(18)));
        div(18,11);
       // int[] a = {1,0,1};
       // System.out.println ( Decimal(a) );
        //int[] array = {1,0,0,1,2,2,2,2};
        //int[] x = devolveSoValorDecimal(array);
        //for (int y = 0; y < x.length; y++)
        //    System.out.print(x[y]);
       
        // System.out.println();
        
    }

    
     
}
