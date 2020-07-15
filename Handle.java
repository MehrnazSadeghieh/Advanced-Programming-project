import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.function.DoubleToIntFunction;

public class Handle {
    Socket socket;
    DataOutputStream output;
    DataInputStream input;
    String string;
    public Handle(Socket socket,DataOutputStream output,DataInputStream input,String string){
        this.input = input;
        this.output = output;
        this.string = string;
        this.socket = socket;
    }

    public Socket getSocket(){
        return socket;
    }

    public DataOutputStream getOutput(){
        return output;
    }

    public DataInputStream getInput(){
        return input;
    }

    public String getString(){
        return string;
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public void setOutput(DataOutputStream output){
        this.output = output;
    }

    public void setInput(DataInputStream input){
        this.input = input;
    }

    public void setString(String string){
        this.string = string;
    }
    public String toString(){
        return "Socket : " + this.socket + " data output : " + this.output + "data input : " + this.input;
    }
}
