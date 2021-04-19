package br.com.laboratory.service;

import br.com.laboratory.entity.Laboratory;
import br.com.laboratory.repository.LaboratoryRepository;
import br.com.laboratory.service.LaboratoryService;
import br.com.laboratory.util.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class LaboratoryServiceTest {

    @InjectMocks
    private LaboratoryService laboratoryService;

    @Mock
    private LaboratoryRepository laboratoryRepository;


    @Before
    public void setup(){
        org.springframework.test.util.ReflectionTestUtils.setField(laboratoryService, "maxItemsPerPage", 50);
    }


    @Test
    public void updateSuccess(){

        Laboratory laboratoryToUpdate = Laboratory.builder().id(1).name("Update Name").address("Rua que muda no update").status(Status.ACTIVE).build();

        Laboratory laboratoryDB = Laboratory.builder().id(1).name("Teste name").address("Endereço que tinha no banco").status(Status.ACTIVE).build();

        Mockito.when(laboratoryRepository.findById(any())).thenReturn(Optional.of(laboratoryDB));
        Mockito.when(laboratoryRepository.save(any())).thenReturn(laboratoryToUpdate);

        Laboratory laboratory = laboratoryService.update(laboratoryToUpdate,0);

        Assert.assertEquals(laboratory.getName(),laboratoryToUpdate.getName());
        Assert.assertEquals(laboratory.getAddress(),laboratoryToUpdate.getAddress());
        Assert.assertEquals(laboratory.getStatus(),Status.ACTIVE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateSetInativeStatus(){


        Laboratory laboratoryToUpdate = Laboratory.builder().id(1).name("Update Name").address("Rua que muda no update").status(Status.INATIVE).build();

        Laboratory laboratoryDB = Laboratory.builder().id(1).name("Teste name").address("Endereço que tinha no banco").status(Status.ACTIVE).build();

        Mockito.when(laboratoryRepository.findById(any())).thenReturn(Optional.of(laboratoryDB));


        laboratoryService.update(laboratoryToUpdate,0);

        Mockito.verify(laboratoryRepository,Mockito.times(0)).save(any());

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void inactiveNotFound(){


        Mockito.when(laboratoryRepository.findById(any())).thenThrow( new EmptyResultDataAccessException("Não existe o campo",1));

        laboratoryService.inactivate(1);

        Mockito.verify(laboratoryRepository,Mockito.times(0)).save(any());

    }

    @Test
    public void inactiveSuccess(){

        Laboratory laboratoryDB = Laboratory.builder().id(1).name("Teste name").address("Endereço que tinha no banco").status(Status.ACTIVE).build();

        Mockito.when(laboratoryRepository.findById(any())).thenReturn(Optional.of(laboratoryDB));

        laboratoryService.inactivate(1);

        Mockito.verify(laboratoryRepository,Mockito.times(1)).save(any());

    }

    @Test
    public void findByOnePage(){

        Laboratory laboratoryDB = Laboratory.builder().id(1).name("Teste name").address("Endereço que tinha no banco").status(Status.ACTIVE).build();

        Mockito.when(laboratoryRepository.findByStatus(any(),any())).thenReturn( new PageImpl(List.of(laboratoryDB)));

        Page<Laboratory> pagedResponse =  laboratoryService.findByStatusPageable(1);

        Assert.assertTrue(pagedResponse.hasContent());

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findNotFound(){

        Mockito.when(laboratoryRepository.findByStatus(any(),any())).thenReturn( new PageImpl(List.of()));

        laboratoryService.findByStatusPageable(1);

    }






}
