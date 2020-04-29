package EllipticCurve;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.ArrayList;
import EllipticCurve.Point;
import EllipticCurve.ElGamal;
import EllipticCurve.MathClass;
import EllipticCurve.Elliptic;

import javax.swing.plaf.IconUIResource;

public class MainCurve {

    public static void main(String[] args) throws IOException {

        //Открываем поток для чтения данных
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Если хотите задать параметры эллиптической кривой введите 1");
        System.out.println("Если хотите использовать дефолтные параметры введите 2 ");

        //Данные для эллиптической кривой
        Elliptic el ;
        Point G ;
        long n ;

        //Данные для реализации шифрсистемы Эль-Гамаля
        long c ;
        ElGamal eg ;
        long m ;
        Point D ;

        //Цикл для считывания информации
        while(true){
            int flag = Integer.parseInt(reader.readLine());
            if (flag == 2) {

                //Инициализация данных для эллиптической кривой
                el = new Elliptic(0,0,0,31988,1000,31991);
                G = new Point(0,5585,el);
                n =32089;

                //Инициализация данных для реализации шифрсистемы Эль-Гамаля
                c = 5103;
                m = 10000;

                break;
            }
            else if(flag == 1){
                System.out.println("Задайте размер поля GF: ");
                long GF = Long.parseLong(reader.readLine());

                System.out.println("Уравнение эллиптической кривой: (y^2 + a1*x*y + a3*y = x^3 + a2*x^2 + a4*x + a5)");

                System.out.println("Задайте a1: ");
                long a1 = Long.parseLong(reader.readLine());

                System.out.println("Задайте a2: ");
                long a2 = Long.parseLong(reader.readLine());

                System.out.println("Задайте a3: ");
                long a3 = Long.parseLong(reader.readLine());

                System.out.println("Задайте a4: ");
                long a4 = Long.parseLong(reader.readLine());

                System.out.println("Задайте a5: ");
                long a5 = Long.parseLong(reader.readLine());

                //Инициализация эллиптической кривой
                el = new Elliptic(a1,a2,a3,a4,a5,GF);

                System.out.println("Задайте точку генератор G: ");
                System.out.println("Задайте x: ");
                long x = Long.parseLong(reader.readLine());
                System.out.println("Задайте y: ");
                long y = Long.parseLong(reader.readLine());

                //Инициализация генератора
                G = new Point(x,y,el);

                System.out.println("Задайте кол-во создаваемых точек: ");
                n = Long.parseLong(reader.readLine());

                System.out.println("Необходимо задать параметры для Эль-Гамаля: ");

                System.out.println("Введите секретный ключ: (число)");
                c = Long.parseLong(reader.readLine());

                System.out.println("Введите сообщение: (число < GF)");
                m = Long.parseLong(reader.readLine());

                break;
            }

        }
        reader.close(); //Закрываем поток

        System.out.println("Список точек: ");
        ArrayList<Point> points = el.genPoints(G,n);
        for (Point p: points) {
            System.out.println(p);
        }
        System.out.println("Время Эль-Гамаля: ");
        eg = new ElGamal(el, G, c);

        //Инициализируем открытый ключ
        D = eg.openKey;
        System.out.println("Открытый ключ: " + D);

        //Шифруем сообщение
        System.out.println("Шифртекст: ");
        System.out.println(eg.encrypt(m,D));

        //Дешифруем сообщение
        System.out.println("Дешифруем: ");
        System.out.println(eg.decrypt(eg.encrypt(m,D), c));
    }
}
