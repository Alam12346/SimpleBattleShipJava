import java.util.Random;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;


public class BattleShips {
    public static void main(String[] args) {

        createOceanMap();
        deployUserShip();
        deployBOTShips();
        BOTTurn();
        playerTurn();
        battle();
    }

    static String[][] ocean=new String[10][10];
    public static List<List> log=new ArrayList();
    public static int playerShips=5;
    public static int BOTShips=5;
    public static void createOceanMap() {

        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < ocean.length; i++) {
            System.out.print(i);
            System.out.print("|");
            for (int j = 0; j < ocean.length; j++) {
                if (ocean[i][j] == null) {
                    System.out.print("  ");
                }
                if (ocean[i][j] == "1") {
                    System.out.print("@ ");
                }
                else if (ocean[i][j] == "2") {
                    System.out.print("  ");
                }
                else if (ocean[i][j] == "x") {
                    System.out.print("x ");
                }
                else if (ocean[i][j] == "!") {
                    System.out.print("! ");
                }
                else if (ocean[i][j] == "-") {
                    System.out.print("- ");
                }

            }
            System.out.print("|");
            System.out.println(i);
        }System.out.println("  0 1 2 3 4 5 6 7 8 9");
    }

    public static void deployUserShip() {
        int shipNo = 1;
        Scanner input = new Scanner(System.in);
        while (shipNo <= 5) {
            System.out.println("Enter the x coordinate of ship" + shipNo+":");
            int x = input.nextInt();
            System.out.println("Enter the y cordinate of ship" + shipNo+":");
            int y = input.nextInt();
            if (x >=0 && x<10 && y>= 0 && y<10) {
                if (ocean[x][y] != null) {
                    System.out.println("Already some ship is present in that location");
                }
                else{
                    ocean[x][y]="1";
                    shipNo++;
                }
            }
            else{
                System.out.println("The coordinate is out of the bettle range");
            }
        }
        createOceanMap();
    }
    public static void deployBOTShips(){
        Random randomNum=new Random();
        int shipNo=1;
        System.out.println("Deploying BOT ships");
        while (shipNo<=5){
            int x=randomNum.nextInt(10);
            int y=randomNum.nextInt(10);
            if (ocean[x][y]==null){
                ocean[x][y]="2";
                System.out.println("ship "+shipNo+" deployed");
                shipNo++;
            }
        }
        createOceanMap();
    }
    public static void attack(int x, int y, String user){
        if(ocean[x][y]==null){
            ocean[x][y]="-";
            if(user.equals("player")){
                System.out.println("You missed");
            }
            if(user.equals("BOT")){
                System.out.println("BOT has missed");
            }
        }
        if(ocean[x][y]=="1"){
            ocean[x][y]="x";
            if (user.equals("player")){
                System.out.println("oh no, you sunk your own ship.. :(");
            }
            if (user.equals("BOT")){
                System.out.println("The BOT sunk your ship");
            }
            --playerShips;
        }
        if (ocean[x][y]=="2"){
            ocean[x][y]="!";
            if(user.equals("player")){
                System.out.println("boom you sunk the ship");
            }
            if(user.equals("BOT")){
                System.out.println("BOT sunk its own ship");
            }
            --BOTShips;
        }
        log.add(turnToList(x,y));
        createOceanMap();
    }
    public static List turnToList(int cor1, int cor2){
        List<Integer> coordinate=new ArrayList<>();
        coordinate.add(cor1);
        coordinate.add(cor2);
        return  coordinate;
    }
    public static void playerTurn(){
        System.out.println("Your turn");
        Scanner input=new Scanner(System.in);
        boolean correctCoordinate=true;
        while (correctCoordinate){
            System.out.println("Enter the x coordinate");
            int x=input.nextInt();
            System.out.println("Enter the y coordinate");
            int y=input.nextInt();
            if(x>=0 && x<10 && y>=0 && y<10){
                if (!log.contains(turnToList(x,y))) {
                    attack(x, y, "player");
                    correctCoordinate=false;
                }
                else{
                    System.out.println("this coordinates has been attacked");
                }
            }
            else{
                System.out.println("the coordinate is out pf the battle range");
            }
        }
    }
    public static void BOTTurn(){
        System.out.println("BOT turn");
        Random randomNum=new Random();
        boolean correctCoordinate=true;
        while(correctCoordinate){
            int x= randomNum.nextInt(10);
            int y= randomNum.nextInt(10);
            if(x>=0 && x<=10 && y>=0 && y<=10) {
                if (!log.contains(turnToList(x, y))) {
                    attack(x, y, "BOT");
                    correctCoordinate = false;
                }
            }
        }

    }
    public static void battle(){
        boolean onBattle=true;
        while (onBattle){
            playerTurn();
            BOTTurn();

            System.out.println("Your ships: " +playerShips+" | BOT ships: "+BOTShips);
            System.out.println("------------------------------");
            if(playerShips==0){
                System.out.println("You lose (┬┬﹏┬┬)");
                onBattle=false;
            }
            if(BOTShips==0){
                System.out.println("Nice Bro You Win (●'◡'●)");
                onBattle=false;
            }
        }
}
}