package com.app.flowchart.controller;


import com.app.flowchart.exception.ChartNotFoundException;
import com.app.flowchart.exception.InvalidEdgeException;
import com.app.flowchart.modal.Edge;
import com.app.flowchart.service.ChartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app")
public class ChartController {

    private final ChartService chartService;

    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping
    public ResponseEntity<?> getChart() {
        long newChartId = chartService.createFlowchart();
        return ResponseEntity.ok( "Chart created with ID : " + newChartId);
    }

    @GetMapping("/flowchart/{id}")
    public ResponseEntity<?> getChartFromId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(chartService.getFlowChart(id));
        } catch (ChartNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllChartIds() {
        return ResponseEntity.ok(chartService.getAllFlowChartIds());
    }

    @PostMapping("/{id}/edge")
    public ResponseEntity<?> addNewEdge(
            @PathVariable Long id,
            @RequestBody Edge newEdge) {
        try {
            chartService.addNewEdge(id, newEdge);
        } catch (InvalidEdgeException ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok().body("Edge added");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFlowchart(@PathVariable Long id) {
        try {
            chartService.deleteFlowChart(id);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok().body("Flowchart ID : " + id + " deleted");
    }

    @DeleteMapping("/{id}/edge")
    public ResponseEntity<?> deleteEdge(
            @PathVariable Long id,
            @RequestBody Edge edge) {
        try {
            chartService.removeEdge(id, edge);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok().body("Edge delete");
    }

    @GetMapping("/{chartId}/{nodeId}/direct")
    public ResponseEntity<?> getAllDirectNodes(@PathVariable Long chartId, @PathVariable Long nodeId) {
        return ResponseEntity.ok().body(chartService.getAllDirectNodesFromNodeId(chartId, nodeId));
    }


    @GetMapping("/{chartId}/{nodeId}/all")
    public ResponseEntity<?> getAllNodes(@PathVariable Long chartId, @PathVariable Long nodeId ) {
        return ResponseEntity.ok().body(chartService.getAllNodesFromNodeId(chartId, nodeId));
    }
}
