package com.app.flowchart.service.Impl;

import com.app.flowchart.exception.ChartNotFoundException;
import com.app.flowchart.modal.Edge;
import com.app.flowchart.modal.Flowchart;
import com.app.flowchart.modal.Node;
import com.app.flowchart.service.ChartService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ChatServiceImpl implements ChartService {

    private final Map<Long,Flowchart> idToFlowchart = new HashMap<>();

    @Override
    public Long createFlowchart() {
        Flowchart flowchart = new Flowchart();
        idToFlowchart.put(flowchart.getId(),flowchart);
        return flowchart.getId();
    }

    @Override
    public List<Long> getAllFlowChartIds() {
        return idToFlowchart.keySet().stream().toList();
    }

    @Override
    public Flowchart getFlowChart(long chartId) {
        if (!idToFlowchart.containsKey(chartId)) {
            throw new ChartNotFoundException("ChartID : " + chartId + " does not exist");
        }
        return idToFlowchart.get(chartId);
    }

    @Override
    public void addNewEdge(long chartId, Edge edge) {
         getFlowChart(chartId).addNewEdge(edge);
    }

    @Override
    public void removeEdge(long chartId, Edge edge) {
       getFlowChart(chartId).removeEdge(edge);
    }

    @Override
    public void deleteFlowChart(long chartId) {
        idToFlowchart.remove(chartId);
    }

    @Override
    public List<Node> getAllDirectNodesFromNodeId(long chartId, long nodeID) {
        return getFlowChart(chartId).getAllDirectNodes(nodeID);
    }

    @Override
    public List<Node> getAllNodesFromNodeId(long chartId, long nodeID) {
        return getFlowChart(chartId).getAllNodes(nodeID);
    }
}
