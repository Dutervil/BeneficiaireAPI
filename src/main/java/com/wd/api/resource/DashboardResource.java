package com.wd.api.resource;

import com.wd.api.service.DashboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.wd.api.constant.AppConstant.MESSAGE;
import static com.wd.api.constant.AppConstant.SOMETHING_WENT_WRONG;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin("*")
public class DashboardResource {

    private final DashboardService dashboardService;

    public DashboardResource(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/count-by-type")
    public ResponseEntity<?> getCountByTypeDepence(){

        try{
            return new ResponseEntity<>(this.dashboardService.getCountByTypeDepence(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }

    @GetMapping("/statistic/total")
    public ResponseEntity<?> statisticAmount(){

        try{
            return new ResponseEntity<>(this.dashboardService.getMapCount(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }

    @GetMapping()
    public ResponseEntity<?> getByCurrencyType(){

        try{
            return new ResponseEntity<>(this.dashboardService.getSumByCurrency(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }
}
