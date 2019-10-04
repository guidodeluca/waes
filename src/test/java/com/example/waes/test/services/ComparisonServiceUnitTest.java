package com.example.waes.test.services;

import com.example.waes.test.api.Response;
import com.example.waes.test.api.ResponseType;
import com.example.waes.test.entities.ComparisonEntity;
import com.example.waes.test.exceptions.IncompleteDataException;
import com.example.waes.test.exceptions.ValidationDataException;
import com.example.waes.test.models.SideEnum;
import com.example.waes.test.repository.ComparisonRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ComparisonServiceUnitTest {
    private static final String COMPARISON_DATA_1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec rhoncus scelerisque quam, nec lobortis risus.";
    private static final String COMPARISON_DATA_2 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas in lorem accumsan, eleifend sem ut nullam.";
    private static final String COMPARISON_DATA_3 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sodales euismod rhoncus. Duis scelerisque blandit.";

    private ComparisonService comparisonService;
    private ComparisonEntity simpleComparisonEntity, leftComparisonEntity, rightComparisonEntity, completeComparisonEntity,
            sameStringComparisonEntity, sameLenghtDiffentStringComparisonEntity, differentLenghtStringComparisonEntity;
    private String uuid, leftData, rightData;

    @Mock
    private ComparisonRepository comparisonRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        comparisonService = new ComparisonService(comparisonRepository);

        uuid = UUID.randomUUID().toString();
        leftData = RandomStringUtils.randomAlphabetic(10);
        rightData = RandomStringUtils.randomAlphabetic(10);

        simpleComparisonEntity = new ComparisonEntity();
        simpleComparisonEntity.setId(uuid);

        leftComparisonEntity = new ComparisonEntity();
        leftComparisonEntity.setId(uuid);
        leftComparisonEntity.setLeft(leftData);

        rightComparisonEntity = new ComparisonEntity();
        rightComparisonEntity.setId(uuid);
        rightComparisonEntity.setRight(rightData);

        completeComparisonEntity = new ComparisonEntity();
        completeComparisonEntity.setId(uuid);
        completeComparisonEntity.setLeft(leftData);
        completeComparisonEntity.setRight(rightData);

        sameStringComparisonEntity = new ComparisonEntity();
        sameStringComparisonEntity.setId(uuid);
        sameStringComparisonEntity.setLeft(COMPARISON_DATA_1);
        sameStringComparisonEntity.setRight(COMPARISON_DATA_1);

        sameLenghtDiffentStringComparisonEntity = new ComparisonEntity();
        sameLenghtDiffentStringComparisonEntity.setId(uuid);
        sameLenghtDiffentStringComparisonEntity.setLeft(COMPARISON_DATA_1);
        sameLenghtDiffentStringComparisonEntity.setRight(COMPARISON_DATA_2);

        differentLenghtStringComparisonEntity = new ComparisonEntity();
        differentLenghtStringComparisonEntity.setId(uuid);
        differentLenghtStringComparisonEntity.setLeft(COMPARISON_DATA_1);
        differentLenghtStringComparisonEntity.setRight(COMPARISON_DATA_3);
    }

    @Test
    public void addLeftDataOfNoExistingData() throws Exception {
        when(comparisonRepository.findById(any())).thenReturn(Optional.empty());
        when(comparisonRepository.save(leftComparisonEntity)).thenReturn(leftComparisonEntity);

        ComparisonEntity result = comparisonService.addData(uuid, leftData, SideEnum.LEFT);

        assertEquals(leftComparisonEntity, result);
    }

    @Test
    public void addLeftDataOfExistingData() throws Exception {
        when(comparisonRepository.findById(any())).thenReturn(Optional.of(simpleComparisonEntity));
        when(comparisonRepository.save(leftComparisonEntity)).thenReturn(leftComparisonEntity);

        ComparisonEntity result = comparisonService.addData(uuid, leftData, SideEnum.LEFT);

        assertEquals(leftComparisonEntity, result);
    }

    @Test
    public void addRightDataOfNoExistingData() throws Exception {
        when(comparisonRepository.findById(any())).thenReturn(Optional.empty());
        when(comparisonRepository.save(rightComparisonEntity)).thenReturn(rightComparisonEntity);

        ComparisonEntity result = comparisonService.addData(uuid, rightData, SideEnum.RIGHT);

        assertEquals(rightComparisonEntity, result);
    }

    @Test
    public void addRighttDataOfExistingData() throws Exception {
        when(comparisonRepository.findById(any())).thenReturn(Optional.of(simpleComparisonEntity));
        when(comparisonRepository.save(rightComparisonEntity)).thenReturn(rightComparisonEntity);

        ComparisonEntity result = comparisonService.addData(uuid, rightData, SideEnum.RIGHT);

        assertEquals(rightComparisonEntity, result);
    }

    @Test
    public void completeDataOfExistingData() throws Exception {
        when(comparisonRepository.findById(any())).thenReturn(Optional.of(leftComparisonEntity));
        when(comparisonRepository.save(completeComparisonEntity)).thenReturn(completeComparisonEntity);

        ComparisonEntity result = comparisonService.addData(uuid, rightData, SideEnum.RIGHT);

        assertEquals(completeComparisonEntity, result);
    }

    @Test(expected = ValidationDataException.class)
    public void zeroLenghtDataException() throws Exception {
        ComparisonEntity result = comparisonService.addData(uuid, anyString(), SideEnum.RIGHT);
    }

    @Test(expected = IncompleteDataException.class)
    public void compareNoData() throws Exception {
        when(comparisonRepository.findById(any())).thenReturn(Optional.empty());
        Response response = comparisonService.compare(uuid);
    }

    @Test(expected = IncompleteDataException.class)
    public void compareIncompleteRightData() throws Exception {
        when(comparisonRepository.findById(any())).thenReturn(Optional.of(leftComparisonEntity));
        Response response = comparisonService.compare(uuid);
    }

    @Test(expected = IncompleteDataException.class)
    public void compareIncompleteLeftData() throws Exception {
        when(comparisonRepository.findById(any())).thenReturn(Optional.of(rightComparisonEntity));
        Response response = comparisonService.compare(uuid);
    }

    @Test
    public void compareSameStringData() throws Exception {
        when(comparisonRepository.findById(any())).thenReturn(Optional.of(sameStringComparisonEntity));
        Response response = comparisonService.compare(uuid);

        assertEquals(ResponseType.EQUALS, response.getType());
        assertTrue(response.getDifferences().size() == 0);
    }

    @Test
    public void compareDifferentLenghtStringData() throws Exception {
        when(comparisonRepository.findById(any())).thenReturn(Optional.of(differentLenghtStringComparisonEntity));
        Response response = comparisonService.compare(uuid);

        assertEquals(ResponseType.NOT_EQUALS, response.getType());
        assertTrue(response.getDifferences().size() == 0);
    }

    @Test
    public void compareSameLenghtDiffentStringData() throws Exception {
        when(comparisonRepository.findById(any())).thenReturn(Optional.of(sameLenghtDiffentStringComparisonEntity));
        Response response = comparisonService.compare(uuid);

        assertEquals(ResponseType.EQUALS, response.getType());
        assertTrue(response.getDifferences().size() > 0);
    }
}
