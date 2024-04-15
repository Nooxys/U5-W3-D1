package CiroVitiello.U5W3D1.services;


import CiroVitiello.U5W3D1.dto.NewEmployeeDTO;
import CiroVitiello.U5W3D1.entities.Employee;
import CiroVitiello.U5W3D1.exceptions.BadRequestException;
import CiroVitiello.U5W3D1.exceptions.NotFoundException;
import CiroVitiello.U5W3D1.repositories.EmployeeDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeDAO ed;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<Employee> getEmployees(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.ed.findAll(pageable);
    }

    public Employee save(NewEmployeeDTO body) {
        this.ed.findByEmail(body.email())
                .ifPresent(employee ->
                {
                    throw new BadRequestException(" email " + employee.getEmail() + " already in use!");
                });

        this.ed.findByUsername(body.username())
                .ifPresent(employee -> {
                    throw new BadRequestException(" username " + employee.getUsername() + " already  in use!");
                });

        return this.ed.save(new Employee(body.username(), body.name(), body.surname(), body.email(), body.password()));
    }

    public Employee findById(long id) {
        return this.ed.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Employee findByIdAndUpdate(long id, NewEmployeeDTO body) {

        Employee found = this.findById(id);

        this.ed.findByEmail(body.email())
                .ifPresent(employee ->
                {
                    throw new BadRequestException(" email " + employee.getEmail() + " already in use!");
                });

        this.ed.findByUsername(body.username())
                .ifPresent(employee -> {
                    throw new BadRequestException(" username " + employee.getUsername() + " already  in use!");
                });


        found.setUsername(body.username());
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setEmail(body.email());
        if (!found.getAvatar().contains("cloudinary")) found.setTemporaryAvatar();
        this.ed.save(found);
        return found;
    }

    public void findByIdAndDelete(long id) {
        Employee found = this.findById(id);
        this.ed.delete(found);
    }

    public Employee uploadImage(MultipartFile image, long employeeId) throws IOException {
        Employee found = findById(employeeId);
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(url);
        ed.save(found);
        return found;
    }

    public Employee findByEmail(String email){
        return ed.findByEmail(email).orElseThrow(() -> new NotFoundException("Employee with " + email + "not found!"));
    }

}

