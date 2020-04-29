package EllipticCurve;

import EllipticCurve.Point;
import EllipticCurve.ElGamal;
import EllipticCurve.Elliptic;

/**
 * Класс с реализацией методов для дополнительных вычислений
 */
public class MathClass {

    /**
     * Метод возвращающий остаток от деления
     * @param a {@long} - делимое
     * @param b {@long} - делитель
     * @return {@long} - возвращает отстаток от деления
     */
    public static long mod(long a, long b){
        if (a >= 0) return a % b;
        else{
            long step = a / b;
            step--;
            step=step*b;
            step=a-step;
            return step;
        }
    }

    /**
     * Метод реализцющий целочисленное деление
     * @param a {@long} - делимое
     * @param b {@long} - делитель
     * @return {@long} - целочисленный результат деления
     */
    public static long div(long a, long b){
        if(a<0)
            if (b<0) return a/b;
            else return a/b-1;
        else if (b<0) return a/b-1;
        else return a/b;
    }

    /**
     * Метод для нахождение обратного элемента
     * (расширенный алгоритм евклида)
     * @param a {@long} - число для которого находим обратное
     * @param GF {@long} - размерность поля
     * @return {@long} - выводит обратный для данного элемента
     */
    public static long mulIn(long a, long GF){
        long s = 0, oldS = 1;
        long t = 1, oldT = 0;
        long r = GF, oldR = a;
        while(r!=0){
            long quotient = div(oldR,r);
            long stepR = r;
            r = oldR - quotient * r;
            oldR = stepR;
            long stepS = s;
            s = oldS - quotient * s;
            oldS = stepS;
            long stepT = t;
            t = oldT - quotient * t;
            oldT = stepT;
        }
        return mod(oldS,GF);
    }
}
