public class FuelQueue {

    private int queueNo;
    private Passenger[] passengerQueue;

    public FuelQueue() {
    }

    //------------------Passenger size initiate---------------------
    public FuelQueue(int queueNo) {
        this.queueNo=queueNo;
        this.passengerQueue=new Passenger[6];
    }

    public Passenger[] getPassengerQueue() {

        return passengerQueue;
    }

    public void setPassengerQueue(Passenger[] queue) {

        this.passengerQueue = queue;
    }

    public int getQueueNo() {

        return queueNo;
    }

    public void setQueueNo(int queueNo) {

        this.queueNo = queueNo;
    }




    public boolean isPassengerQueueFull(){
        if(passengerQueue[5]!=null){
            return true;
        }else{
            return false;
        }
    }
}
