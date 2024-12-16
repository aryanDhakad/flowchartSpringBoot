package com.app.flowchart.service;

import com.app.flowchart.modal.Edge;
import com.app.flowchart.modal.Flowchart;
import com.app.flowchart.modal.Node;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChartService {

    Long createFlowchart();

    List<Long> getAllFlowChartIds();

    Flowchart getFlowChart(long chartId);

    void addNewEdge(long chartId, Edge edge);

    void removeEdge(long chartId,Edge edge);

    void deleteFlowChart(long chartId);

    List<Node> getAllDirectNodesFromNodeId(long chartId,long nodeID);

    List<Node> getAllNodesFromNodeId(long chartId, long nodeID);
}


