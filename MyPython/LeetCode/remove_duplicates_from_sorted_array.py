from typing import List


class Solution:
    def removeDuplicates(self, nums: List[int]) -> int:
        # 0,0,1,1,1,2,2,3,3,4
        #     ^                i
        #   ^                  next_avail
        # 0 1 1,1,1,2,2,3,3,4
        #           ^
        #     ^
        # 0 1 2,1,1,2,2,3,3,4
        #             ^ 
        #       ^
        # 0 1 2,3,1,2,2,3,3,4
        #               ^
        #         ^
        # 0 1 2,3,4,2,2,3,3,4
        #                   ^
        #           ^
        if len(nums) == 0:
            return 1
        next_avail = 1
        for i in range(1, len(nums)):
            if nums[i] != nums[next_avail - 1]:
                nums[next_avail] = nums[i]
                next_avail += 1
        return next_avail

if __name__ == "__main__":
    nums = [1 ,1, 2]
    assert Solution().removeDuplicates(nums) == 2