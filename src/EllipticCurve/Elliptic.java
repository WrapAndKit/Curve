package EllipticCurve;

import java.util.ArrayList;

import EllipticCurve.Point;
import EllipticCurve.ElGamal;
import EllipticCurve.MathClass;

/**
 * Класс для работы с эллиптической кривой
 */
public class Elliptic {
    long a1,a2,a3,a4,a5,GF;

    /**
     * Конструктор эллиптической кривой
     * @param a1 {@long} - параметр
     * @param a2 {@long} - параметр
     * @param a3 {@long} - параметр
     * @param a4 {@long} - параметр
     * @param a5 {@long} - параметр
     * @param GF {@long} - размерность поля
     */
    public Elliptic(long a1, long a2, long a3, long a4, long a5, long GF){
        assert MathClass.mod((4 * a4 *a4 *a4 + 27 * a5 * a5), GF) != 0;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.a5 = a5;
        this.GF = GF;
    }

    /**
     * Метод для определения лежит ли точка на прямой
     * @param point{@Point} - точка
     * @return {@boolean} - возвращаем значение true, если лежит, иначе false
     */
    public boolean contains(Point point){
        long x = point.x;
        long y = point.y;
        if ((x == 0) &&(y == 0)) return true;
        long GF = this.GF;
        long leftRes = MathClass.mod(y * y + this.a1 * x * y + this.a3 * y,GF);
        long rightRes = MathClass.mod(x * x * x + this.a2 * x * x + this.a4 * x + this.a5,GF);
        return leftRes == rightRes;

    }

    /**
     * Метод для преобразования данных в строку
     * @return {@String} - возвращает строку с информацией об эллиптической кривой
     */
    public String toString(){
        return "y^2 + " + this.a1 + "xy + " + this.a3 + "y = x^3 + " + this.a2 + "x^2 + " + this.a4 + "x + " + this.a5;
    }

    /**
     * Метод для генерации точек
     * @param generator {@Point} - точка генератор
     * @param n {@long} - кол-во генерируемых точек
     * @return {@ArrayList<@Point>} - возвращает список сгенерированных точек
     */
    public ArrayList<Point> genPoints(Point generator, long n){
        ArrayList<Point> res = new ArrayList<>();
        Point g = generator;
        for (long i = 0; i < n; i++) {
            res.add(generator);
            generator = generator.add(g);
        }
        return res;
    }
}
