"""
Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.

Example:

Input: [0,1,0,3,12]
Output: [1,3,12,0,0]
Note:

You must do this in-place without making a copy of the array.
Minimize the total number of operations.
"""
from typing import List


def swap(nums: List[int], i: int, j: int) -> None:
    temp = nums[i]
    nums[i] = nums[j]
    nums[j] = temp

class Solution:
    def moveZeroes(self, nums: List[int]) -> None:
        """
        Do not return anything, modify nums in-place instead.
        """
        # 0
        # 1
        # 0 1
        # j i
        # 1 0
        # i j
        # 0 1 3
        # 0 1 0 3 -> 1 0 0 3 -> 1 3 0 0
        # 0 1 3 0 4 -> 1 0 3 0 4 -> 1 3 0 0 4 -> 1 3 4 0 0
        # ^ ^            ^ ^            ^   ^
        # 1 0 5 6 4 0 3 0 2
        #   ^ ^
        # 1 5 0 6 4 0 3 0 2
        #     ^ ^
        # 1 5 6 0 4 0 3 0 2
        #       ^ ^   
        # 1 5 6 4 3 0 0 0 2
        #           ^     ^
        i, j, l = 0, 0, len(nums)
        while i < l and j < l:
            if nums[i] == 0:
                i += 1
                continue
            elif nums[j] != 0:
                j += 1
                continue
            elif i > j:
                swap(nums, i, j)
                continue
            else: # i <= j
                i += 1
