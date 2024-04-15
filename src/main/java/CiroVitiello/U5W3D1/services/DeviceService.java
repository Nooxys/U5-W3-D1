package CiroVitiello.U5W3D1.services;


import CiroVitiello.U5W3D1.dto.AssignDeviceDTO;
import CiroVitiello.U5W3D1.dto.NewDeviceDTO;
import CiroVitiello.U5W3D1.dto.UploadDeviceDTO;
import CiroVitiello.U5W3D1.entities.Device;
import CiroVitiello.U5W3D1.exceptions.BadRequestException;
import CiroVitiello.U5W3D1.exceptions.NotFoundException;
import CiroVitiello.U5W3D1.repositories.DeviceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    @Autowired
    private DeviceDAO dd;

    @Autowired
    private EmployeeService es;

    public Page<Device> getDevices(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.dd.findAll(pageable);
    }

    public Device save(NewDeviceDTO body) {
        return this.dd.save(new Device(body.typology()));
    }

    public Device findById(long id) {
        return this.dd.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Device findByIdAndUpdate(long id, UploadDeviceDTO body) {
        if (!body.status().equals("Assigned")) {
            Device found = this.findById(id);
            found.setStatus(body.status());
            found.setEmployee(null);
            dd.save(found);
            return found;
        } else throw new BadRequestException("The device cannot be set to Assigned");

    }

    public void findByIdAndDelete(long id) {
        Device found = this.findById(id);
        this.dd.delete(found);
    }

    public Device findByIdAndAssign(long id, AssignDeviceDTO body) {
        Device found = this.findById(id);

//        QUI FACCIO UN CONTROLLO SULLO SPAZIO INIZIALE DI AVAILABLE PERCHE' PER QUALCHE MOTIVO ALTRIMENTI SI BUGGA E MI LANCIA L'ERRORE
//        " This device is currently  Available"  SU POSTMAN IN ALCUNE CIRCOSTANZE

        if (found.getStatus().equals(" Available") || found.getStatus().equals("Available") || found.getStatus().equals("Assigned")) {
            found.setStatus("Assigned");
            found.setEmployee(es.findById(body.employeeId()));
            dd.save(found);
            return found;
        } else throw new BadRequestException(" This device is currently " + found.getStatus());
    }
}
