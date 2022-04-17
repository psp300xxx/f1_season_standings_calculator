package controller;

public class JSONDownloadException extends Exception {
    private Exception superExc;

    public JSONDownloadException(){

    }

    public JSONDownloadException(Exception superExc){
        this.superExc = superExc;
    }

    public Exception getSuperExc(){
        return this.superExc;
    }

    @Override
    public String getMessage() {
        if(this.superExc==null){
            return "Generic error happend";
        }
        return this.superExc.getMessage();
    }
}
