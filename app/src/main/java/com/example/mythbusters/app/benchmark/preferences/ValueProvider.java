package com.example.mythbusters.app.benchmark.preferences;

import com.example.mythbusters.core.benchmark.io.MeasureWriteReadUseCase;

/**
 * Provides {@link String} value for {@link MeasureWriteReadUseCase}
 */
public class ValueProvider implements MeasureWriteReadUseCase.ValueProvider {

    @Override
    public String provide() {
        return "http://example.com";
    }

}
