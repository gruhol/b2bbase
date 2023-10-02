package pl.thinkdata.b2bbase.company.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.company.dto.BranchDto;
import pl.thinkdata.b2bbase.company.dto.BranchResponse;
import pl.thinkdata.b2bbase.company.dto.BranchToEditDto;
import pl.thinkdata.b2bbase.company.model.Branch;
import pl.thinkdata.b2bbase.company.service.BranchService;

import java.util.List;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/list")
    public List<BranchResponse> getBranchByCompany(HttpServletRequest request) {
        return branchService.getBranchByCompany(request);
    }

    @PostMapping
    public Branch addBranch(@RequestBody @Valid BranchDto branchDto, HttpServletRequest request) {
        return branchService.addBranch(branchDto, request);
    }

    @PutMapping
    public Branch editBranch(@RequestBody @Valid BranchToEditDto branchToEditDto, HttpServletRequest request) {
        return branchService.editBranch(branchToEditDto, request);
    }

    @DeleteMapping("/{id}")
    public void deleteBranch(@PathVariable(value = "id", required = false) Long id, HttpServletRequest request) {
        branchService.deleteBranch(id, request);
    }

    @GetMapping("/{id}")
    public BranchResponse getBranch(@PathVariable(value = "id", required = false) Long id, HttpServletRequest request) {
        return branchService.getBranch(id, request);
    }
}
