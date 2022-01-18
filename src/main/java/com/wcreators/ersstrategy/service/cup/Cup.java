package com.wcreators.ersstrategy.service.cup;

import com.wcreators.ersstrategy.model.CupRatePoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Cup {
    private final List<CupRatePoint> points = new ArrayList<>();

    public void addPoint(CupRatePoint value) {
        points.add(value);
        if (points.size() > 150) {
            points.remove(0);
        }
    }

    public int size() {
        return points.size();
    }

    public CupRatePoint get(int index) {
        return points.get(index);
    }
}
