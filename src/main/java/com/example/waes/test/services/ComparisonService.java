package com.example.waes.test.services;

import com.example.waes.test.api.Difference;
import com.example.waes.test.api.DifferenceType;
import com.example.waes.test.api.Response;
import com.example.waes.test.api.ResponseType;
import com.example.waes.test.entities.ComparisonEntity;
import com.example.waes.test.exceptions.IncompleteDataException;
import com.example.waes.test.exceptions.ValidationDataException;
import com.example.waes.test.models.SideEnum;
import com.example.waes.test.repository.ComparisonRepository;
import com.example.waes.test.utils.DiffMatchPatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ComparisonService {
    private ComparisonRepository comparisonRepository;

    @Autowired
    public ComparisonService(ComparisonRepository comparisonRepository) {
        this.comparisonRepository = comparisonRepository;
    }

    public ComparisonEntity addData(String id, String data, SideEnum side) throws ValidationDataException{
        if (StringUtils.isBlank(data)) {
            throw new ValidationDataException(ValidationDataException.DATA_ZEROLENGHT);
        }

        Optional<ComparisonEntity> optComparison = comparisonRepository.findById(id);

        ComparisonEntity comparison;
        if (optComparison.isPresent()) {
            comparison = optComparison.get();
        } else {
            comparison = new ComparisonEntity();
            comparison.setId(id);
        }

        // I asume that data can be overwrite
        if (side.equals(SideEnum.LEFT)) {
            comparison.setLeft(data);
        } else {
            comparison.setRight(data);
        }

        return comparisonRepository.save(comparison);
    }

    public Response compare(String id) throws IncompleteDataException {
        Optional<ComparisonEntity> optComparison = comparisonRepository.findById(id);

        ComparisonEntity comparison = optComparison.orElseThrow(() -> new IncompleteDataException(IncompleteDataException.NO_DATA));

        if (StringUtils.isBlank(comparison.getLeft())) {
            throw new IncompleteDataException(IncompleteDataException.NO_LEFT_DATA);
        } else if (StringUtils.isBlank(comparison.getRight())) {
            throw new IncompleteDataException(IncompleteDataException.NO_RIGHT_DATA);
        }

        Response response = new Response();
        if (comparison.getLeft().length() == comparison.getRight().length()) {
            response.setType(ResponseType.EQUALS);
            if (!comparison.getLeft().equalsIgnoreCase(comparison.getRight())) {
                response.setDifferences(this.calculateDifferences(comparison.getLeft(), comparison.getRight()));
            }
        } else {
            response.setType(ResponseType.NOT_EQUALS);
        }

        return response;
    }

    private List<Difference> calculateDifferences(String left, String right) {
        List<Difference> differences = new ArrayList<>();
        DiffMatchPatch diffMatchPatch = new DiffMatchPatch();
        LinkedList<DiffMatchPatch.Diff> diffs = diffMatchPatch.diff_main(left, right, false);

        for (DiffMatchPatch.Diff diff : diffs) {
            Difference difference = new Difference();
            switch (diff.operation) {
                case EQUAL:
                    continue;
                case DELETE:
                    difference.setDifferenceType(DifferenceType.DELETE);
                    difference.setText(diff.text);
                    difference.setPosition(left.indexOf(diff.text));
                    difference.setOffset(diff.text.length());
                    break;
                case INSERT:
                    difference.setDifferenceType(DifferenceType.INSERT);
                    difference.setText(diff.text);
                    difference.setPosition(right.indexOf(diff.text));
                    difference.setOffset(diff.text.length());
                    break;
            }

            differences.add(difference);
        }

        return differences;
    }
}
