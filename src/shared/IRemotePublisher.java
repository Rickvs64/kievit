package shared;

import java.rmi.RemoteException;

public interface IRemotePublisher {
    void addListener(IListener listener)  throws RemoteException;
    void removeListener(IListener listener)  throws RemoteException;
}

