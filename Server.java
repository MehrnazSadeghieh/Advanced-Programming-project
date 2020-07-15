import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server{
    static ArrayList<Handle> testHandles = new ArrayList<>();

    public static void savingTestHandle() throws IOException, ClassNotFoundException {
        ///ObjectInputStream oi = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("testHandles.txt"))));
        FileInputStream fi = new FileInputStream(new File("testHandles.txt"));
        ObjectInputStream oi = new ObjectInputStream(fi);
        System.out.println("making input stream");
        for (int i = 0 ; fi.available() != 0 ;i++){
            testHandles.add((Handle) oi.readObject());
            System.out.println("loading first for");
        }
        ///ObjectOutputStream o = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("testHandles.txt"))));
        FileOutputStream f = new FileOutputStream(new File("testHandles.txt"));
        ObjectOutputStream o = new ObjectOutputStream(f);
        System.out.println("making output stream");
        for (int i = 0; i < testHandles.size(); i++){
            o.writeObject(testHandles.get(i));
            System.out.println("loading second for");
        }

    }
    public static void savingTestHandle1(){
        try {
            FileInputStream fi = new FileInputStream(new File("testHandles.dat"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            FileOutputStream f = new FileOutputStream(new File("testHandles.dat"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            int bytes = fi.available();
            System.out.println(bytes);
            if(fi.available()!=0){

            }
            for (int i = 0; i < testHandles.size(); i++){
                o.writeObject(testHandles.get(i));
            }

            for (int i = 0; fi.available()!=0; i++){
                testHandles.add((Handle) oi.readObject());
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<Handle> getTestHandles() {
        return testHandles;
    }

    public static void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(7773);

        while(true){
            Socket client = null;
            try{
                DataOutputStream output;
                DataInputStream input;

                client = serverSocket.accept();
                ////System.out.println("dkbdk");
                output = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
                output.writeUTF("Hello this is server");
                output.flush();
                input = new DataInputStream(new BufferedInputStream(client.getInputStream()));
                ////System.out.println(input.readUTF());
                Handle testHandle = new Handle(client,output,input,input.readUTF());
                testHandles.add(testHandle);
                System.out.println("Handle added");
                ///savingTestHandle();
                System.out.println("saving handle");
                savingTestHandle1();
                /*for (int i=0;i<testHandles.size();i++){
                    testHandles.get(i).toString();
                }*/



            }
            catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public void handler(){

    }

    public static void main(String[] args) throws IOException {
        start();
    }
}