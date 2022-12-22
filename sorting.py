import pygame
import random
import math
pygame.init()

class DrawInfo:
    BLACK = 0, 0, 0
    WHITE = 255, 255, 255
    GREEN = 0, 255, 0
    RED = 255, 0, 0
    GREY = 128, 128, 128
    BLUE = 0, 255, 255
    PURPLE = 127, 0, 255
    BACKGROUND_COLOR = 204, 204, 255
    SIDE_PAD = 100
    TOP_PAD = 150
    PINK = 255, 0, 127
    GRADIENTS = [
        GREY,
        (160, 160, 160),
        (192, 192, 192)
    ]
    SORTING_COLOR = PINK

    FONT = pygame.font.SysFont('Helvetica', 30)
    LARGE = pygame.font.SysFont('Helvetica', 40)

    def __init__(self, width, height, list):
        self.width = width
        self.height = height

        self.window = pygame.display.set_mode((width, height))
        pygame.display.set_caption("Sorting Algorithm Visualization")
        self.set_list(list)

    def set_list(self, list):
        self.list = list
        self.min_val = min(list)
        self.max_val = max(list)

        self.block_width = round((self.width - self.SIDE_PAD) / len(list))
        self.block_height = math.floor((self.height - self.TOP_PAD) / (self.max_val - self.min_val))
        self.start_x = self.SIDE_PAD // 2

def draw(draw_info, sort_name, ascending):
    draw_info.window.fill(draw_info.BACKGROUND_COLOR)

    controls = draw_info.FONT.render("Press Space to start and r to reset!", 1, draw_info.BLACK)
    draw_info.window.blit(controls, (draw_info.width/2 - controls.get_width()/2,15))

    sorting_options = draw_info.FONT.render("B for Bubble Sort or M for merge sort.", 1, draw_info.BLACK)
    draw_info.window.blit(sorting_options, (draw_info.width/2 - sorting_options.get_width()/2,55))

    current_sort = draw_info.LARGE.render(f"{sort_name}", 1, draw_info.SORTING_COLOR)
    draw_info.window.blit(current_sort, (draw_info.width/2 - current_sort.get_width()/2,95))

    draw_list(draw_info)
    pygame.display.update()

def draw_list(draw_info, color_positions={}, clear_back=False):
    list = draw_info.list
    if clear_back:
        clear_rect = (draw_info.SIDE_PAD//2, draw_info.TOP_PAD, 
                    draw_info.width - draw_info.SIDE_PAD, draw_info.height - draw_info.TOP_PAD)

        pygame.draw.rect(draw_info.window, draw_info.BACKGROUND_COLOR, clear_rect)

    for i, n, in enumerate(list):
        x = draw_info.start_x + i * draw_info.block_width
        y = draw_info.height - (n - draw_info.min_val) * draw_info.block_height 

        color = draw_info.GRADIENTS[i%3]

        if i in color_positions:
            color = color_positions[i]

        pygame.draw.rect(draw_info.window, color, (x,y, draw_info.block_width, draw_info.height))

        if clear_back:
            pygame.display.update()

def starting_list(n, min, max):
    list = []

    for _ in range(n):
        val = random.randint(min, max)
        list.append(val)

    return list

def bubble_sort(draw_info, ascending=True):
    list = draw_info.list

    for i in range(len(list) - 1):
        for j in range(len(list) - 1 - i):
            num1 = list[j]
            num2 = list[j+1]
            if (num1 > num2 and ascending) or (num1 < num2 and not ascending):
                list[j], list[j+1] = list[j+1], list[j]
                draw_list(draw_info, {j: draw_info.BLUE, j+1: draw_info.PURPLE}, True)
                yield True

    return list

def heap(arr, n, i):
      largest = i
      l = 2 * i + 1
      r = 2 * i + 2
  
      if l < n and arr[i] < arr[l]:
          largest = l
  
      if r < n and arr[largest] < arr[r]:
          largest = r

      if largest != i:
          arr[i], arr[largest] = arr[largest], arr[i]
          heap(arr, n, largest)


def heap_sort(draw_info, ascending=True):
    list = draw_info.list
    length = len(list)

    for i in range (length//2, -1, -1):
        heap(list, length, i)
        draw_list(draw_info, {i: draw_info.BLUE, i+1: draw_info.PURPLE}, True)
        yield True


    for i in range(length-1, 0, -1):
        list[i], list[0] = list[0], list[i]
        heap(list, i, 0)
        draw_list(draw_info, {i: draw_info.BLUE, i+1: draw_info.PURPLE}, True)
        yield True

    return list

def main():
    run = True
    clock = pygame.time.Clock()
    n = 50
    min = 0
    max = 100
    list = starting_list(n, min, max)
    draw_info = DrawInfo(800, 600, list)
    sorting = False
    ascending = True

    sorting_alg = bubble_sort
    sorting_alg_name = "Bubble Sort"
    sorting_alg_generator = None
    clock_speed = 60
    while run:
        clock.tick(clock_speed)
        if sorting:
            try:
                next(sorting_alg_generator)
            except StopIteration:
                draw_info.GRADIENTS = [
                    (9, 121, 105),
                    (80, 200, 120),
                    (175, 225, 175)
                ]
                # sorting_alg_name = f"Succesfully Sorted with {sorting_alg_name}!"
                sorting = False
        else:
            draw(draw_info, sorting_alg_name, ascending)
        

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                run = False
            
            if event.type != pygame.KEYDOWN:
                continue
            elif event.key == pygame.K_r:
                draw_info.GRADIENTS = [
                    draw_info.GREY,
                    (160, 160, 160),
                    (192, 192, 192)
                ]

                list = starting_list(n, min, max)
                draw_info.set_list(list)
                sorting = False
            elif event.key == pygame.K_SPACE and sorting == False:
                sorting = True
                sorting_alg_generator = sorting_alg(draw_info, ascending)
            elif event.key == pygame.K_a and sorting == False:
                ascending = True
            elif event.key == pygame.K_d and sorting == False:
                ascending = False
            elif event.key == pygame.K_w:
                clock_speed -= 10
            elif event.key == pygame.K_s:
                clock_speed += 10
            elif event.key == pygame.K_b and not sorting:
                draw_info.SORTING_COLOR = draw_info.PINK
                sorting_alg_name = "Bubble Sorting..."
                sorting_alg = bubble_sort
            elif event.key == pygame.K_h and not sorting:
                draw_info.SORTING_COLOR = draw_info.GREEN
                sorting_alg_name = "Heap Sorting..."
                sorting_alg = heap_sort
    pygame.quit()

if __name__ == "__main__":
    main()

