package com.kim.dibt.services.corporateuser;

import com.kim.dibt.core.utils.business.CustomModelMapper;
import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.SuccessDataResult;
import com.kim.dibt.models.CorprateUser;
import com.kim.dibt.repo.CityRepository;
import com.kim.dibt.repo.CorporateUserRepository;
import com.kim.dibt.repo.MentionRepository;
import com.kim.dibt.services.corporateuser.dtos.*;
import kotlin.jvm.internal.SerializedIr;
import lombok.RequiredArgsConstructor;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CorporateUserManager implements CorporateUserService {
    private final CorporateUserRepository corporateUserRepository;
    private final CustomModelMapper modelMapper;
    private final CityRepository cityRepository;
    private final MentionRepository mentionRepository;

    @Override
    public DataResult<AddedCorporateUserDto> add(AddCorporateUserDto addCorporateUserDto) {

//        String jsonData = "";
//        try {
//            jsonData = new String(Files.readAllBytes(Paths.get("C:\\Users\\iso\\IdeaProjects\\dibt-backend\\belediyelerfull.json")));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        JSONArray jsonArray = new JSONArray(jsonData);
//
//        /*
//         {
//    "Plaka Kodu": "1",
//    "Ad": "Adana Büyükşehir Belediyesi",
//    "Web Sitesi": "http://www.adana.bel.tr/",
//    "Data URL": "https://www.turkiye.gov.tr/adana-buyuksehir-belediyesi",
//    "Kurum Adı": "Adana Büyükşehir Belediyesi",
//    "Telefon": "+90 322 455 35 00",
//    "Adres": "Adana Büyükşehir Belediyesi Reşatbey Mah. Atatürk Cad. SEYHAN ADANA TÜRKİYE",
//    "Logo": "https://cdn.e-devlet.gov.tr/themes/ankara/images/logos/64px/11559.1.8.0.png"
//  },
//         */
//        for (int i = 0; i < jsonArray.length(); i++) {
//            CorprateUser corprateUser = new CorprateUser();
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            corprateUser.setName(jsonObject.getString("Ad"));
//            corprateUser.setPhone(jsonObject.getString("Telefon"));
//            corprateUser.setAddress(jsonObject.getString("Adres"));
//            corprateUser.setWebsite(jsonObject.getString("Web Sitesi"));
//            corprateUser.setLogo(jsonObject.getString("Logo"));
//            corprateUser.setAbout(jsonObject.getString("Kurum Adı"));
//            corprateUser.setUsername(generateUsername(jsonObject.getString("Ad")));
//            corprateUser.setEmail("info@" + corprateUser.getUsername() + ".com");
//            corprateUser.setPassword(bCryptPasswordEncoder.encode("123456"));
//            cityRepository.findById(jsonObject.getLong("Plaka Kodu")).ifPresent(corprateUser::setCity);
//            corporateUserRepository.save(corprateUser);
//        }

        return SuccessDataResult.of(null, "Kurum başarıyla eklendi.");
    }
    //    public String generateUsername(String text) {
//        String[] turkceKarakterler = {"ç", "Ç", "ğ", "Ğ", "ı", "İ", "ö", "Ö", "ş", "Ş", "ü", "Ü"};
//        String[] ingilizceKarakterler = {"c", "C", "g", "G", "i", "I", "o", "O", "s", "S", "u", "U"};
//
//        // Türkçe karakterleri İngilizce karakterlere dönüştür
//        for (int i = 0; i < turkceKarakterler.length; i++) {
//            text = text.replaceAll(turkceKarakterler[i], ingilizceKarakterler[i]);
//        }
//
//        // Boşlukları kaldır, harfleri küçült ve "-" ekle
//
//        return text.toLowerCase().replaceAll("(?!^)-|-$", "").replaceAll("\\s+", "-");
//    }


    @Override
    public DataResult<List<GetDetailOfCorporateUser>> getByCityId(Long cityId) {
        List<CorprateUser> corprateUsers = corporateUserRepository.findAllByCityId(cityId);
        List<GetDetailOfCorporateUser> getDetailOfCorporateUsers = modelMapper.mapList(corprateUsers, GetDetailOfCorporateUser.class);
        getDetailOfCorporateUsers.forEach(getDetailOfCorporateUser -> {
            getDetailOfCorporateUser.setComplaintCount(mentionRepository.countAllByUserId(getDetailOfCorporateUser.getId()));
        });
        setColorOfCorporateUsers(getDetailOfCorporateUsers);
        return SuccessDataResult.of(getDetailOfCorporateUsers, "Kurumlar başarıyla getirildi.");
    }

    @Override
    public DataResult<GetDetailOfCorporateUser> getByUsername(String username) {
        GetDetailOfCorporateUser corprateUser = corporateUserRepository.findByUsername(username);
        return SuccessDataResult.of(corprateUser, "Kurum başarıyla getirildi.");
    }

    @Override
    public DataResult<List<MapDto>> getMap() {
        Long totalCount = corporateUserRepository.countAl();
        List<GetDetailOfCorporateUserForMapDto> dataList = corporateUserRepository.getAllDetailOfCorporate();

        Map<Long, Long> complaintCountMap = dataList.stream()
                .collect(Collectors.groupingBy(GetDetailOfCorporateUserForMapDto::getCityId, Collectors.summingLong(GetDetailOfCorporateUserForMapDto::getComplaintCount)));

        List<MapDto> result = complaintCountMap.entrySet()
                .stream()
                .map(entry -> new MapDto(entry.getKey(), entry.getKey(), entry.getValue(), determineColor(entry.getValue(), totalCount)))
                .toList();

        return SuccessDataResult.of(result, "Kurumlar başarıyla getirildi.");
    }

    private void setColorOfCorporateUsers(List<GetDetailOfCorporateUser> getDetailOfCorporateUsers) {
        long sumOfComplaints = getDetailOfCorporateUsers.stream().mapToLong(GetDetailOfCorporateUser::getComplaintCount).sum();
        getDetailOfCorporateUsers.forEach(getDetailOfCorporateUser -> {
            long complaintCount = getDetailOfCorporateUser.getComplaintCount();
            getDetailOfCorporateUser.setColor(determineColor(complaintCount, sumOfComplaints));
        });
    }

    private String determineColor(long complaintCount, long totalCount) {
        if (complaintCount == 0) {
            return "#008000"; // Yeşil
        } else if (complaintCount <= totalCount / 8) {
            return "#006400"; // Koyu yeşil
        } else if (complaintCount <= totalCount / 7) {
            return "#ADFF2F"; // Yeşil sarı
        } else if (complaintCount <= totalCount / 6) {
            return "#FFFF00"; // Sarı
        } else if (complaintCount <= totalCount / 5) {
            return "#FFA500"; // Turuncu
        } else if (complaintCount <= totalCount / 4) {
            return "#FF4500"; // Koyu turuncu
        } else if (complaintCount <= totalCount / 3) {
            return "#FF0000"; // Kırmızı
        } else {
            return "#FF0000"; // Kırmızı (eğer complaintCount totalCount / 2'den büyükse)
        }
    }


}
