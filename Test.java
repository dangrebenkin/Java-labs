package New_collection_interface;

public class Test {

    // создаем массив элементов нашей коллекции и выводим на экран (поскольку в
    // нашем случае это строки, то можно использваоть класс StringBuilder)
    public static void pretty_string(Objects_Box<String> obj){
        Object[] arr = obj.toArray();
        StringBuilder sb = new StringBuilder();
        for (Object o : arr) {
            sb.append(o);
            sb.append("; ");
        }
        System.out.println("Элементы коллекции: " + sb);
    }

    public static void main(String[] args) {

        // создаем объект класса Objects_Box, допустим мы хотим создать коллекцию из объектов класса String
        Objects_Box<String> simple_box = new Objects_Box<>();

        // создаём и добавляем элементы в Objects_Box
        String one = "Новый элемент";
        String two = "Второй элемент";
        String three = "Третий элемент";
        String four = "Четвертый элемент";
        String five = "Пятый элемент";
        String six = "Шестой элемент";

        simple_box.add(one);
        simple_box.add(two);
        simple_box.add(three);
        pretty_string(simple_box);
        System.out.println();

        System.out.printf("Размер коллекции: %d \n", simple_box.size());
        System.out.printf("Объект '%s' находится в коллекции? %b \n", one, simple_box.contains(one));
        System.out.printf("Объект '%s' удалось удалить? %b \n", three, simple_box.remove(three)); // true
        System.out.printf("Коллекция пустая? %b \n", simple_box.isEmpty());
        simple_box.clear();
        System.out.println();

        Objects_Box<String> simple_box1 = new Objects_Box<>();
        System.out.printf("Объект '%s' удалось удалить из пустой коллекции? %b \n", three, simple_box1.remove(three)); // false

        Objects_Box<String> simple_box2 = new Objects_Box<>();
        Objects_Box<String> simple_box3 = new Objects_Box<>();

        simple_box2.add(five);
        simple_box2.add(three);

        simple_box3.add(four);
        simple_box3.add(five);
        simple_box3.add(six);

        System.out.printf("Коллекция simple_box2 содержит все элементы simple_box3? %b \n", simple_box2.containsAll(simple_box3));
        System.out.printf("Удалось удалить все элементы simple_box3 из simple_box2? %b \n",simple_box3.removeAll(simple_box2));
        pretty_string(simple_box3);


    }
}

