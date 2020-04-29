package EllipticCurve;

import EllipticCurve.Point;
import EllipticCurve.MathClass;
import EllipticCurve.Elliptic;

/**
 * Класс для работы с шифрование Эль-Гамаля
 */
public class ElGamal {

    /**
     * Класс представляющий шифртекст
     */
    public static class Pair {
        Point R;
        long e;

        public Pair(Point r, long e) {
            R = r;
            this.e = e;
        }

        public String toString(){
            return "||" + R.x + "||" + R.y + "||" + e + "||";
        }
    }

    long power; // размерность поля
    Point openKey, generator; //открытый ключ и генератор
    Elliptic curve; // эллиптическая кривая

    /**
     * Конструктор класса
     * @param curve {@Elliptic} - эллиптическая кривая
     * @param generator {@Point} - точка для генераци открытого ключа
     * @param secretKey {@long} - секретный ключ с помощью которого генерируем открытый ключ
     */
    public ElGamal(Elliptic curve, Point generator, long secretKey) {
        this.generator = generator;
        this.curve = curve;
        long d = 1;
        Point point = generator;
        Point O = new Point(0, 0, curve);
        //Цикл для выявления размерности
        while (!point.equal(O)) {
            point = point.add(generator);
            d++;
        }
        this.power = d;
        this.openKey = generator.mul(secretKey);
    }

    /**
     * Метод для создания шифртекста
     * @param mes {@long} - сообщение которое мы шифруем
     * @param key {@Point} - открытый ключ
     * @return {@Pair} - возвращает шифртекст
     */
    public Pair encrypt(long mes, Point key) {

        //вообще k должно быть случайным
        long k = 523;

        Point R = this.generator.mul(k);
        Point P = key.mul(k);
        long e = MathClass.mod((mes * P.x), this.curve.GF);
        return new Pair(R, e);
    }

    /**
     * Метод для дешифровки шифртекста
     * @param enc {@Pair} - ширтекст
     * @param secretKey {@long} - секретный ключ для дешифровки
     * @return {@long} - возвращает исходное соообщение
     */
    public long decrypt(Pair enc, long secretKey) {
        Point Q = enc.R.mul(secretKey);
        long mes = MathClass.mod((enc.e * MathClass.mulIn(Q.x, this.curve.GF)), this.curve.GF);
        return mes;
    }
}
