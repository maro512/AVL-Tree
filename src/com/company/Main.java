package com.company;

import java.util.Scanner;
import java.util.TreeSet;

class Node {
    public char info;        // zawartość węzła
    public Node left;        // lewy następnik
    public Node right;     // prawy następnik
    public Node up;         // poprzednik
    public int H;               // wysokość węzła
    public int DH(){
        if (left != null){
            if (right != null) return left.H - right.H;
            return H;
        } else if (right != null) return -H;
        return 0;
    }; // balans węzła (różnica wysokości lewego i prawego poddrzewa)
}

class BSTTree {
    private Node r;  // poniższe nazwy rotacji są w notacji z wykładu

    public void rotL(char x) {
        Node y = find(x);
        if (y == null) {
            System.out.println("Brak wezla");
            return;
        }

        Node p = y.left;
        if (p == null) {
            System.out.println("Nieprawidłowa operacja");
            return;
        }

        y.left = p.right;
        if (p.right != null) p.right.up = y;
        p.right = y;

        p.up = y.up;
        y.up = p;
        if (r == y) r = p;
        else if (p.up.left == y) p.up.left = p;
        else p.up.right = p;

        y.H = height(y);
        p.H = height(p);

        changeH(p);
    }

    public void rotR(char x) {
        Node y = find(x);
        if (y == null) {
            System.out.println("Brak wezla");
            return;
        }

        Node p = y.right;
        if (p == null) {
            System.out.println("Nieprawidłowa operacja");
            return;
        }

        y.right = p.left;
        if (p.left != null) p.left.up = y;
        p.left = y;

        p.up = y.up;
        y.up = p;
        if (r == y) r = p;
        else if (p.up.left == y) p.up.left = p;
        else p.up.right = p;

        y.H = height(y);
        p.H = height(p);

        changeH(p);
    }

    public void rotLL(char x) {
        Node h = find(x);
        if (h == null) {
            System.out.println("Brak wezla");
            return;
        }

        Node l = h.left;
        if (l == null) {
            System.out.println("Nieprawidłowa operacja");
            return;
        }
        Node n = l.left;
        if (n == null) {
            System.out.println("Nieprawidłowa operacja");
            return;
        }

        h.left = l.right;
        l.left = n.right;
        l.up = n;
        n.up = h.up;

        if (n.right != null) n.right.up = l;
        n.right = l;

        if (l.right != null) l.right.up = h;
        l.right = h;

        if (h == r) r = n;
        else if (h.up.left == h) h.up.left = n;
        else h.up.right = n;
        h.up = l;

        h.H = height(h);
        l.H = height(l);
        n.H = height(n);

        changeH(n);
    }

    public void rotLR(char x) {
        Node h = find(x);
        if (h == null) {
            System.out.println("Brak wezla");
            return;
        }

        Node l = h.left;
        if (l == null) {
            System.out.println("Nieprawidłowa operacja");
            return;
        }
        Node n = l.right;
        if (n == null) {
            System.out.println("Nieprawidłowa operacja");
            return;
        }

        h.left = n.right;
        l.right = n.left;
        l.up = n;
        n.up = h.up;

        if (n.left != null) n.left.up = l;
        n.left = l;

        if (n.right != null) n.right.up = h;
        n.right = h;

        if (h == r) r = n;
        else if (h.up.left == h) h.up.left = n;
        else h.up.right = n;
        h.up = n;

        h.H = height(h);
        l.H = height(l);
        n.H = height(n);

        changeH(n);
    }

    public void rotRL(char x) {
        Node h = find(x);
        if (h == null) {
            System.out.println("Brak wezla");
            return;
        }

        Node l = h.right;
        if (l == null) {
            System.out.println("Nieprawidłowa operacja");
            return;
        }
        Node n = l.left;
        if (n == null) {
            System.out.println("Nieprawidłowa operacja");
            return;
        }

        h.right = n.left;
        l.left = n.right;
        l.up = n;
        n.up = h.up;

        if (n.left != null) n.left.up = h;
        n.left = h;

        if (n.right != null) n.right.up = l;
        n.right = l;

        if (h == r) r = n;
        else if (h.up.left == h) h.up.left = n;
        else h.up.right = n;
        h.up = n;

        h.H = height(h);
        l.H = height(l);
        n.H = height(n);

        changeH(n);
    }

    public void rotRR(char x) {
        Node h = find(x);
        if (h == null) {
            System.out.println("Brak wezla");
            return;
        }

        Node l = h.right;
        if (l == null) {
            System.out.println("Nieprawidłowa operacja");
            return;
        }
        Node n = l.right;
        if (n == null) {
            System.out.println("Nieprawidłowa operacja");
            return;
        }

        h.right = l.left;
        l.right = n.left;
        l.up = n;
        n.up = h.up;

        if (n.left != null) n.left.up = l;
        n.left = l;

        if (l.left != null) l.left.up = h;
        l.left = h;

        if (h == r) r = n;
        else if (h.up.left == h) h.up.left = n;
        else h.up.right = n;
        h.up = l;

        h.H = height(h);
        l.H = height(l);
        n.H = height(n);

        changeH(n);
    }

    public void insertBST(char x) {
        Node n = new Node();
        n.info = x;
        n.H = 0;
        if (r == null) r = n;
        else {
            Node prev = null;
            for (Node p = r; p != null; p = (x < p.info) ? p.left : p.right) {
                if (p.info == x){
                    System.out.println("Istnieje węzeł " + x);
                    return;
                }
                prev = p;
            }
            if (x < prev.info) prev.left = n;
            else prev.right = n;
            n.up = prev;
            for (int i = 1; prev != null; i++, prev = prev.up){
                if (prev.H >= i) break;
                prev.H = i;
            }
        }
    }

    public void printTree() {
        int h = r.H+1;
        for (int i = 1; i <= h; i++) {
            int space = 2;
            for (int j = h; j >= i; j--) space *= 2;
            disp(r, i, space - 1);
            System.out.println();
        }
    }

    private Node find(char x) {
        for (Node p = r; p != null; p = (x < p.info) ? p.left : p.right)
            if (p.info == x) return p;

        return null;
    }

    private int height(Node p) {
        if (p.left != null) {
            if (p.right != null) return p.left.H > p.right.H ? p.left.H + 1 : p.right.H + 1;
            return p.left.H + 1;
        } else if (p.right != null) return p.right.H + 1;
        return 0;
    }

    private void changeH(Node p) {
        Node prev = p.up;
        for (int i = p.H + 1; prev != null; i++, prev = prev.up){
            if (prev.H >= i) break;
            prev.H = i;
        }
    }

    private void disp(Node p, int level, int space) {
        if (p == null) {
            int lvl = 1;
            for (int i = 1; i < level; i++) lvl *= 2;
            while (lvl-- > 0) {
                for (int i = 0; i < 2 * (space + 1); i++) System.out.print(" ");
            }
            return;
        }
        if (level == 1) {
            for (int i = 0; i < space - 2; i++) System.out.print(" ");
            System.out.printf("%c (%2d)", p.info, p.DH());
            for (int i = 0; i < space - 2; i++) System.out.print(" ");
        } else {
            disp(p.left, level - 1, space);
            disp(p.right, level - 1, space);
        }
    }


}

//    napisz implementacje powyższych operacji w drzewie AVL.
//
//        Program wypisuje menu zawierające operacje na drzewie,przy czym wybranie na przykład:
//
//        rotL(char x)-spowoduje wykonanie lewej rotacji dla węzła x,w przypadku braku węzła o podanym kluczu wypisuje odpowiedni komunikat.
//        insertBST(char x) – spowoduje wstawienie węzła x do drzewa BST,jeśli takiego węzła nie było w drzewie,w przeciwnym przypadku wypisuje odpowiedni komunikat.
//        printTree() – spowoduje wypisanie drzewa w formie:
//

public class Main {

    public static void menu() {
        System.out.println();
        System.out.println("Operacje na drzewie BST:");
        System.out.println("====================");
        System.out.println("[1] Dodaj elementy do drzewa (wpisanie zera przerywa operację)");
        System.out.println("[2] Wykonaj rotację pojedynczą L");
        System.out.println("[3] Wykonaj rotację pojedynczą R");
        System.out.println("[4] Wykonaj rotację podwójną LL");
        System.out.println("[5] Wykonaj rotację podwójną LR");
        System.out.println("[6] Wykonaj rotację podwójną RL");
        System.out.println("[7] Wykonaj rotację podwójną RR");
        System.out.println("[8] Wyswietl");
        System.out.println("[9] Koniec");
    }

    public static char read(String s){
        return s.charAt(0);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        BSTTree t = new BSTTree();
        char ch;
        boolean exit = true;
        do {
            menu();
            switch (in.nextInt()) {
                case 1:
                    while (true){
                        System.out.print("Wpisz znak oznaczający węzeł: ");
                        ch = read(in.next());
                        if (ch == '0') break;
                        t.insertBST(ch);
                    }
                    break;
                case 2:
                    System.out.print("Wpisz znak oznaczający węzeł: ");
                    ch = read(in.next());
                    t.rotL(ch);
                    break;
                case 3:
                    System.out.print("Wpisz znak oznaczający węzeł: ");
                    ch = read(in.next());
                    t.rotR(ch);
                    break;
                case 4:
                    System.out.print("Wpisz znak oznaczający węzeł: ");
                    ch = read(in.next());
                    t.rotLL(ch);
                    break;
                case 5:
                    System.out.print("Wpisz znak oznaczający węzeł: ");
                    ch = read(in.next());
                    t.rotLR(ch);
                    break;
                case 6:
                    System.out.print("Wpisz znak oznaczający węzeł: ");
                    ch = read(in.next());
                    t.rotRL(ch);
                    break;
                case 7:
                    System.out.print("Wpisz znak oznaczający węzeł: ");
                    ch = read(in.next());
                    t.rotRR(ch);
                    break;
                case 8:
                    t.printTree();
                    break;
                case 9:
                    exit = false;
            }
        } while (exit);
    }
    TreeSet
}
