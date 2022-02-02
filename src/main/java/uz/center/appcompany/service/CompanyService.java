package uz.center.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.center.appcompany.entity.Address;
import uz.center.appcompany.entity.Company;
import uz.center.appcompany.payload.ApiResponse;
import uz.center.appcompany.payload.CompanyDto;
import uz.center.appcompany.repository.AddressRepository;
import uz.center.appcompany.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CompanyRepository companyRepository;

    public List<Company> all(){
        List<Company> allCompany = companyRepository.findAll();;
        return allCompany;
    }

    public Company getCompany(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }

    public ApiResponse saveCompany(CompanyDto dto){
        boolean existsByCorpName = companyRepository.existsByCorpName(dto.getCorpName());
        if (existsByCorpName){
            return new ApiResponse("Bunday kompaniya mavjud", false);
        }

        boolean homeNumberAndStreet = addressRepository.existsByHomeNumberAndStreet(dto.getHomeNumber(), dto.getStreet());
        if (homeNumberAndStreet){
            return new ApiResponse("Ushbu adressda boshqa kompaniya mavjud", false);
        }

        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setHomeNumber(dto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);

        Company company = new Company();
        company.setCorpName(dto.getCorpName());
        company.setDirectorName(dto.getDirectorName());
        company.setAddress(savedAddress);

        Company savedCompany = companyRepository.save(company);

        return new ApiResponse("Saved", true);
    }

    public ApiResponse editCompany(CompanyDto dto, Integer id){
        try {
            boolean nameAndIdNot = companyRepository.existsByCorpNameAndIdNot(dto.getCorpName(), id);
            if (nameAndIdNot){
                return new ApiResponse("Bunday kompaniya mavjud", false);
            }

            Optional<Company> optionalCompany = companyRepository.findById(id);
            if (!optionalCompany.isPresent()){
                return new ApiResponse("Bunday kompaniya mavjud emas", false);
            }

            Company company = optionalCompany.get();

            Address address1 = company.getAddress();

            boolean homeNumberAndStreet = addressRepository.existsByHomeNumberAndStreetAndIdNot(dto.getHomeNumber(), dto.getStreet(), id);
            if (homeNumberAndStreet){
                return new ApiResponse("Ushbu adressda boshqa kompaniya mavjud", false);
            }

            address1.setStreet(dto.getStreet());
            address1.setHomeNumber(dto.getHomeNumber());
            Address savedAddress = addressRepository.save(address1);

            company.setAddress(savedAddress);
            company.setCorpName(dto.getCorpName());
            company.setDirectorName(dto.getDirectorName());
            companyRepository.save(company);
            return new ApiResponse("Edited", true);
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse remove(Integer id) {
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("Deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

}
