import java.util.*;

class KonwerterMacierzyGrafu {

    public static void main(String[] args) {
        Scanner skaner = new Scanner(System.in);

        System.out.println("\n\n\nTwórcy: Mielcarek (indeks Krzysia) oraz Megger (76594)");

        boolean kontynuacja = true;
        while (kontynuacja) {
            System.out.println("\nWybierz operację:");
            System.out.println("1. Zamiana macierzy sąsiedztwa na macierz incydencji");
            System.out.println("2. Zamiana macierzy incydencji na macierz sąsiedztwa");
            System.out.println("3. Reprezentacja grafu za pomocą listy sąsiedztwa");
            System.out.println("4. Zakończ działanie programu");
            int wybor = skaner.nextInt();

            switch (wybor) {
                case 1 -> zamienSasiedztwoNaIncydencje(skaner);
                case 2 -> zamienIncydencjeNaSasiedztwo(skaner);
                case 3 -> obslugaListySasiedztwa(skaner);
                case 4 -> {
                    System.out.println("Dziękujemy za skorzystanie z programu! Do zobaczenia :)");
                    kontynuacja = false;
                }
                default -> System.out.println("Nieprawidłowy wybór! Wybierz 1, 2, 3 lub 4.");
            }
        }

        skaner.close();
    }

    private static void zamienSasiedztwoNaIncydencje(Scanner skaner) {
        System.out.println("Podaj liczbę wierzchołków:");
        int liczbaWierzcholkow = skaner.nextInt();
        if (liczbaWierzcholkow <= 0) {
            System.out.println("Liczba wierzchołków musi być dodatnia!");
            return;
        }

        int[][] macierzSasiedztwa = new int[liczbaWierzcholkow][liczbaWierzcholkow];
        System.out.println("Podaj macierz sąsiedztwa (wierszami):");

        for (int i = 0; i < liczbaWierzcholkow; i++) {
            for (int j = 0; j < liczbaWierzcholkow; j++) {
                macierzSasiedztwa[i][j] = skaner.nextInt();
                if (macierzSasiedztwa[i][j] != 0 && macierzSasiedztwa[i][j] != 1) {
                    System.out.println("Macierz sąsiedztwa może zawierać tylko wartości 0 lub 1.");
                    return;
                }
            }
        }

        List<int[]> listaKrawedzi = new ArrayList<>();
        for (int i = 0; i < liczbaWierzcholkow; i++) {
            for (int j = i; j < liczbaWierzcholkow; j++) { // Obsługa grafów nieskierowanych
                if (macierzSasiedztwa[i][j] == 1) {
                    listaKrawedzi.add(new int[]{i, j});
                }
            }
        }

        int[][] macierzIncydencji = new int[liczbaWierzcholkow][listaKrawedzi.size()];
        for (int indeksKrawedzi = 0; indeksKrawedzi < listaKrawedzi.size(); indeksKrawedzi++) {
            int[] krawedz = listaKrawedzi.get(indeksKrawedzi);
            int poczatek = krawedz[0];
            int koniec = krawedz[1];
            macierzIncydencji[poczatek][indeksKrawedzi] = 1;
            macierzIncydencji[koniec][indeksKrawedzi] = 1;
        }

        System.out.println("Macierz incydencji:");
        wyswietlMacierz(macierzIncydencji);
    }

    private static void zamienIncydencjeNaSasiedztwo(Scanner skaner) {
        System.out.println("Podaj liczbę wierzchołków:");
        int liczbaWierzcholkow = skaner.nextInt();
        System.out.println("Podaj liczbę krawędzi:");
        int liczbaKrawedzi = skaner.nextInt();

        if (liczbaWierzcholkow <= 0 || liczbaKrawedzi <= 0) {
            System.out.println("Liczba wierzchołków i krawędzi musi być dodatnia!");
            return;
        }

        int[][] macierzIncydencji = new int[liczbaWierzcholkow][liczbaKrawedzi];
        System.out.println("Podaj macierz incydencji (wierszami):");

        for (int i = 0; i < liczbaWierzcholkow; i++) {
            for (int j = 0; j < liczbaKrawedzi; j++) {
                macierzIncydencji[i][j] = skaner.nextInt();
                if (macierzIncydencji[i][j] != 0 && macierzIncydencji[i][j] != 1) {
                    System.out.println("Macierz incydencji może zawierać tylko wartości 0 lub 1.");
                    return;
                }
            }
        }

        int[][] macierzSasiedztwa = new int[liczbaWierzcholkow][liczbaWierzcholkow];
        for (int j = 0; j < liczbaKrawedzi; j++) {
            List<Integer> wierzcholki = new ArrayList<>();
            for (int i = 0; i < liczbaWierzcholkow; i++) {
                if (macierzIncydencji[i][j] == 1) {
                    wierzcholki.add(i);
                }
            }
            if (wierzcholki.size() == 2) {
                macierzSasiedztwa[wierzcholki.get(0)][wierzcholki.get(1)] = 1;
                macierzSasiedztwa[wierzcholki.get(1)][wierzcholki.get(0)] = 1; // Graf nieskierowany
            }
        }

        System.out.println("Macierz sąsiedztwa:");
        wyswietlMacierz(macierzSasiedztwa);
    }

    private static void obslugaListySasiedztwa(Scanner skaner) {
        System.out.println("Podaj liczbę wierzchołków:");
        int liczbaWierzcholkow = skaner.nextInt();
        if (liczbaWierzcholkow <= 0) {
            System.out.println("Liczba wierzchołków musi być dodatnia!");
            return;
        }

        List<Set<Integer>> listaSasiedztwa = new ArrayList<>();
        for (int i = 0; i < liczbaWierzcholkow; i++) {
            listaSasiedztwa.add(new HashSet<>());
        }

        System.out.println("Podaj liczbę krawędzi:");
        int liczbaKrawedzi = skaner.nextInt();
        if (liczbaKrawedzi < 0) {
            System.out.println("Liczba krawędzi musi być nieujemna!");
            return;
        }

        System.out.println("Podaj krawędzie (pary wierzchołków, od 1 do " + liczbaWierzcholkow + "):");
        for (int i = 0; i < liczbaKrawedzi; i++) {
            int wierzcholek1 = skaner.nextInt() - 1;
            int wierzcholek2 = skaner.nextInt() - 1;
            if (wierzcholek1 < 0 || wierzcholek1 >= liczbaWierzcholkow || wierzcholek2 < 0 || wierzcholek2 >= liczbaWierzcholkow) {
                System.out.println("Nieprawidłowe wierzchołki! Podaj wartości od 1 do " + liczbaWierzcholkow);
                return;
            }
            listaSasiedztwa.get(wierzcholek1).add(wierzcholek2);
            listaSasiedztwa.get(wierzcholek2).add(wierzcholek1);
        }

        System.out.println("Lista sąsiedztwa:");
        wyswietlListeSasiedztwa(listaSasiedztwa);

        for (int i = 0; i < liczbaWierzcholkow; i++) {
            for (int sasied : listaSasiedztwa.get(i)) {
                System.out.println("Krawędź " + (i + 1) + " łączy się z " + (sasied + 1));
            }
        }
    }

    private static void wyswietlMacierz(int[][] macierz) {
        for (int[] wiersz : macierz) {
            for (int wartosc : wiersz) {
                System.out.print(wartosc + " ");
            }
            System.out.println();
        }
    }

    private static void wyswietlListeSasiedztwa(List<Set<Integer>> listaSasiedztwa) {
        for (int i = 0; i < listaSasiedztwa.size(); i++) {
            System.out.print((i + 1) + ": ");
            for (int sasiad : listaSasiedztwa.get(i)) {
                System.out.print((sasiad + 1) + " ");
            }
            System.out.println();
        }
    }
}
