package com.example;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.mfcc.MFCC;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class MyClass {
    public static void main(String[] args){
        AudioDispatcher audioDispatcher = AudioDispatcherFactory.fromFloatArray();
        PitchDetectionHandler pitchDetectionHandler = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
                pitchDetectionResult
            }
        };
        audioDispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.YIN,44100,2048,pitchDetectionHandler));
        MFCC
    }
}
