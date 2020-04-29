package EllipticCurve;

import EllipticCurve.ElGamal;
import EllipticCurve.MathClass;
import EllipticCurve.Elliptic;

/**
 * Класс для работы с точками
 */
public class Point {
    long x,y; // координаты точки
    Elliptic curve; // эллиптическая кривая

    /**
     * Конструктор точки
     * @param x {@long} - координата x
     * @param y {@long} - координата y
     * @param curve {@Elliptic} - эллиптическая кривая
     *
     */
    public Point(long x, long y, Elliptic curve){
        this.x = x;
        this.y = y;
        this.curve = curve;
        assert this.curve.contains(this);
    }

    /**
     * Преобразование в строку
     * @return {@String} - возвращаем строку с координатами точки
     */
    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }

    /**
     * Метод для сравнения двух точек
     * @param other {@Point} - точка для сравнения
     * @return {@boolean} - возвращаем true, если точки равны, иначе false
     */
    public boolean equal(Point other){
        return (other.x == this.x) && (other.y == this.y);
    }

    /**
     * Метод возвращающий противоположную точку
     * @return {@Point}
     */
    public Point negative(){
        long y = MathClass.mod(-this.y, this.curve.GF);
        return  new Point(this.x, y, this.curve);
    }

    /**
     * Метод реализующий сложение двух точек
     * @param other{@Point} - второе слагаемое
     * @return {@Point} - возвращаем значение сложения
     */
    public Point add(Point other){
        long GF = this.curve.GF,
                a1 = this.curve.a1,
                a2 = this.curve.a2,
                a3 = this.curve.a3,
                a4 = this.curve.a4,
                a5 = this.curve.a5,
                x1 = this.x,
                y1 = this.y,
                x2 = other.x,
                y2 = other.y;
        Point O = new Point(0,0,this.curve);
        long l,v;
        if (this.equal(O)) return other; // Если наша точка O
        else if (other.equal(O)) return this; // Если слагаемое - точка O
        else if (this.equal(other.negative())) return O;
        else if (!this.equal(other)) { //Если точка не равны
            l = MathClass.mod(((y2 - y1) * MathClass.mulIn(x2 - x1, GF)),GF);
            v = MathClass.mod(((y1 * x2 - y2 * x1) * MathClass.mulIn(x2 - x1, GF)),GF);
        }
        else{//Если точки равны
            l = MathClass.mod(((3 * x1 * x1 + 2 * a2 * x1 + a4 - a1 * y1) * MathClass.mulIn(2 * y1 + a1 * x1 + a3, GF)),GF);
            v = MathClass.mod(((-x1 * x1 * x1 + a4 * x1 + 2 * a5 - a3 * y1) * MathClass.mulIn(2 * y1 + a1 * x1 + a3, GF)),GF);
        }
        //Находим значение x и y
        long resX = MathClass.mod((l * l + a1 * l - a2 - x1 - x2) , GF);
        long resY = MathClass.mod((-(l + a1) * resX - v - a3) , GF);
        return new Point(resX, resY, this.curve);
    }

    /**
     * Умножение точки на число
     * @param n {@long} - число на которое умножаем точку
     * @return {@Point} - результат умножения
     */
    public Point mul(long n){
        Point res = this;
        if (n == 0) return new Point(0,0,this.curve);
        for (long i = 0; i < n-1; i++) {
            res = res.add(this);
        }
        return res;
    }
}
