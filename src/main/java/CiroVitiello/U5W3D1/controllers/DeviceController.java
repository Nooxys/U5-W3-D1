package CiroVitiello.U5W3D1.controllers;


import CiroVitiello.U5W3D1.dto.AssignDeviceDTO;
import CiroVitiello.U5W3D1.dto.NewDeviceDTO;
import CiroVitiello.U5W3D1.dto.UploadDeviceDTO;
import CiroVitiello.U5W3D1.entities.Device;
import CiroVitiello.U5W3D1.exceptions.BadRequestException;
import CiroVitiello.U5W3D1.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService ds;

    @GetMapping
    private Page<Device> getAllDevices(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sortBy) {
        return this.ds.getDevices(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Device saveDevice(@RequestBody NewDeviceDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return ds.save(body);
    }

    @GetMapping("/{deviceId}")
    private Device findDeviceById(@PathVariable long deviceId) {
        return ds.findById(deviceId);
    }

    @PutMapping("/{deviceId}")
    private Device findDeviceByIdAndUpdate(@PathVariable long deviceId, @RequestBody UploadDeviceDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return ds.findByIdAndUpdate(deviceId, body);
    }

    @DeleteMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void findDeviceByIdAndDelete(@PathVariable long deviceId) {
        ds.findByIdAndDelete(deviceId);
    }

    @PutMapping("/assign/{deviceId}")
    private Device findByIdAndAssign(@PathVariable long deviceId, @RequestBody AssignDeviceDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return ds.findByIdAndAssign(deviceId, body);
    }

}