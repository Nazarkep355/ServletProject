package com.example.finalproject3.Utility;

import com.example.finalproject3.Entity.Train;

public class TrainUtility {
   static public String fromTo(Train train){
        return String.format(" %s - %s",train.getStations().get(0).getName(),
                train.getStations().get(train.getStations().size()-1).getName());
    }
}
