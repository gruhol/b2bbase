package pl.thinkdata.b2bbase.company.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.thinkdata.b2bbase.company.dto.BranchDto;
import pl.thinkdata.b2bbase.company.model.Branch;
import pl.thinkdata.b2bbase.company.service.BranchService;

import java.util.List;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/list")
    public List<Branch> getBranchByCompany(HttpServletRequest request) {
        return branchService.getBranchByCompany(request);
    }

    @PostMapping
    public  Branch addBranch(@RequestBody @Valid BranchDto branchDto, HttpServletRequest request) {
        return branchService.addBranch(branchDto, request);
    }
}
