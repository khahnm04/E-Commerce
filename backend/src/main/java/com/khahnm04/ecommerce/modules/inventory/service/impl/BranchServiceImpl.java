package com.khahnm04.ecommerce.modules.inventory.service.impl;

import com.khahnm04.ecommerce.shared.enums.BranchStatus;
import com.khahnm04.ecommerce.shared.util.SortUtils;
import com.khahnm04.ecommerce.modules.inventory.dto.request.BranchRequest;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.inventory.dto.response.BranchResponse;
import com.khahnm04.ecommerce.modules.inventory.entity.Branch;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.inventory.mapper.BranchMapper;
import com.khahnm04.ecommerce.modules.inventory.repository.BranchRepository;
import com.khahnm04.ecommerce.modules.inventory.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Override
    public BranchResponse createBranch(BranchRequest request) {
        if (branchRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.BRANCH_NAME_EXISTED);
        }
        Branch branch = branchMapper.toEntity(request);
        branch = branchRepository.save(branch);
        log.info("Branch created with id: {}", branch.getId());
        return branchMapper.toResponse(branch);
    }

    @Override
    public BranchResponse updateBranch(Long id, BranchRequest request) {
        Branch branch = getBranchById(id);
        if (StringUtils.hasText(request.getName())
                && branchRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), branch.getId())) {
            throw new AppException(ErrorCode.BRANCH_NAME_EXISTED);
        }
        branchMapper.updateBranch(branch, request);
        branch = branchRepository.save(branch);
        log.info("Branch updated with id: {}", branch.getId());
        return branchMapper.toResponse(branch);
    }

    @Override
    public void softDeleteBranch(Long id) {
        Branch branch = getBranchById(id);
        branch.setDeletedAt(LocalDateTime.now());
        branchRepository.save(branch);
        log.info("Branch soft deleted with id: {}", branch.getId());
    }

    @Override
    public BranchResponse getBranchDetailById(Long id) {
        return branchMapper.toResponse(getBranchById(id));
    }

    @Override
    public PageResponse<BranchResponse> getBranchesAdmin(int page, int size, String sort, String keyword, BranchStatus status) {
        Sort sortObj = SortUtils.parseSort(sort);
        Pageable pageable = PageRequest.of(page - 1, size, sortObj);
        Page<Branch> pageData = branchRepository.searchBranches(keyword, status, pageable);
        Page<BranchResponse> dtoPage = pageData.map(branchMapper::toResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public List<BranchResponse> getAllActiveBranches(String city) {
        List<Branch> branches;
        if (StringUtils.hasText(city)) {
            branches = branchRepository.findAllByCityAndStatus(city, BranchStatus.ACTIVE);
        } else {
            branches = branchRepository.findAllByStatus(BranchStatus.ACTIVE);
        }
        return branches.stream()
                .map(branchMapper::toResponse)
                .toList();
    }

    private Branch getBranchById(Long id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BRANCH_NOT_FOUND));
    }

}
