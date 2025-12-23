package org.example.ticket.booking.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ticket.booking.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService {

    private Train train;

    private List<Train> trainList;

    ObjectMapper mapper = new ObjectMapper();

    private final String Train_PATH ="app/src/main/resources/LocalDb/Train.json";



    public TrainService(Train train) throws IOException {
     this.train = train;
     trainList=getAllTrains();
    }

    public TrainService() throws IOException {
        getAllTrains();
    }

    public List<Train> getAllTrains() throws IOException {
        File train=new File(Train_PATH);
        return mapper.readValue(train,new  TypeReference<List<Train>>(){});

    }
    public List<Train> SearchTrains(String source,String destination) {
        return trainList.stream().filter(train->validTrain(train,source,destination)).collect(Collectors.toList());
    }

    private boolean validTrain(Train train, String source, String destination) {
        if(source.equals(destination)) {
            return false;
        }

        List<String> stationOrder = train.getStations();

        int sourceIndex = stationOrder.indexOf(source.toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.toLowerCase());

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }




}
